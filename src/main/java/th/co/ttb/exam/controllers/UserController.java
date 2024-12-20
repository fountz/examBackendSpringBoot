package th.co.ttb.exam.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.internet.MimeMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import th.co.ttb.exam.models.ApiResponseError;
import th.co.ttb.exam.entities.User;
import th.co.ttb.exam.services.UserService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/apis/user")
public class UserController {
    private final static Logger LOG = LogManager.getLogger(UserController.class);
    @Autowired
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private JavaMailSender mailSender;

    private String md5Encode(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            LOG.error("MD5 algorithm not found", e);
            return null;
        }
    }

    @RequestMapping(value = "datatable", method = RequestMethod.POST)
    public DataTablesOutput<User> getListForDataTables(@RequestBody DataTablesInput input) {
        try {
            LOG.info("SUCCESS => apis/user/datatable : " + input);
            return userService.findAll(input);
        } catch (Exception ex) {
            LOG.error("ERROR => apis/user/datatable: ", ex);
            // Return an empty DataTablesOutput in case of an error
            DataTablesOutput<User> errorOutput = new DataTablesOutput<>();
            errorOutput.setError("An error occurred while processing your request.");
            return errorOutput;
        }
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        try {
            LOG.info("SUCCESS => apis/user/save : " + user);
            return ResponseEntity.ok(userService.save(user));
        } catch (Exception ex) {
            LOG.error("ERROR => apis/user/save : " + user);
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body(new ApiResponseError("US0001" +
                    "", "Can not save new user"));
        }
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody String body) {
        JSONObject req = new JSONObject(body);
        try {
            if (req.getString("uuid") != null) {
                LOG.info("SUCCESS => apis/user/delete : " + req);
                return ResponseEntity.ok(userService.delete(req.getString("uuid")));
            } else {
                LOG.error("WARN => apis/user/delete : " + req);
                return ResponseEntity.internalServerError().body(new ApiResponseError("US0001", "Can not remove user"));
            }
        } catch (Exception ex) {
            LOG.error("ERROR => apis/user/delete : " + req);
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body(new ApiResponseError("US0001" +
                    "", "Can not remove user"));
        }
    }

    @RequestMapping(value = "changePWD", method = RequestMethod.POST)
    public ResponseEntity<?> changePassword(@RequestBody String body) {
        JSONObject req = new JSONObject(body);
        try {
            if (req.getString("uuid") != null) {
                User user = userService.findById(req.getString("uuid"));
                String oldPasswordHash = md5Encode(req.getString("oldPassword"));
                String newPasswordHash = md5Encode(req.getString("newPassword"));
                if (user.getPassword() == null || user.getPassword().equals("") || user.getPassword().equals(oldPasswordHash)) {
                    user.setPassword(newPasswordHash);
                    userService.save(user);
                    LOG.info("SUCCESS => apis/user/changePWD : " + req);
                    return ResponseEntity.ok("success");
                } else {
                    LOG.error("ERROR => apis/user/changePWD wrong password : " + req);
                    return ResponseEntity.internalServerError().body(new ApiResponseError("US0001", "wrong password"));
                }
            } else {
                LOG.error("ERROR => apis/user/changePWD : " + req);
                return ResponseEntity.internalServerError().body(new ApiResponseError("US0001", "uuid is empty"));
            }
        } catch (Exception ex) {
            LOG.error("ERROR => apis/user/changePWD : " + req);
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body(new ApiResponseError("US0001" +
                    "", "Can not change password"));
        }
    }

    @RequestMapping(value = "getData", method = RequestMethod.POST)
    public ResponseEntity<?> getData(@RequestBody String body) {
        JSONObject req = new JSONObject(body);
        try {
            if (req.getString("uuid") != null) {
                User user = userService.findById(req.getString("uuid"));
                LOG.info("SUCCESS => apis/getData : " + req);
                return ResponseEntity.ok(user);
            } else {
                LOG.error("ERROR => apis/user/getData uuid is empty : " + req);
                return ResponseEntity.internalServerError().body(new ApiResponseError("US0001", "uuid is empty"));
            }
        } catch (Exception ex) {
            LOG.error("ERROR => apis/user/getData : " + req);
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body(new ApiResponseError("US0001" +
                    "", "Can not get data"));
        }
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody String body) {
        JSONObject req = new JSONObject(body);
        try {
            if (req.getString("email") != null && req.getString("password") != null && !req.getString("password").equals("")) {
                String PasswordHash = md5Encode(req.getString("password"));
                User user = userService.findByEmailAndPassword(req.getString("email"), PasswordHash);
                LOG.info("SUCCESS => apis/user/login : " + req);
                return ResponseEntity.ok(user);
            } else {
                LOG.error("ERROR => apis/user/login data empty : " + req);
                return ResponseEntity.internalServerError().body(new ApiResponseError("US0001", "data empty"));
            }
        } catch (Exception ex) {
            LOG.error("ERROR => apis/user/login : " + req);
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body(new ApiResponseError("US0001" +
                    "", "Can not log in"));
        }
    }

    @RequestMapping(value = "checkmail", method = RequestMethod.POST)
    public ResponseEntity<?> checkMail(@RequestBody String body) {
        JSONObject req = new JSONObject(body);
        try {
            if (req.getString("email") != null) {
                Boolean check = userService.findByEmail(req.getString("email"));
                LOG.info("SUCCESS => apis/user/checkmail : " + req);
                return ResponseEntity.ok(check);
            } else {
                LOG.error("ERROR => apis/user/checkmail data empty : " + req);
                return ResponseEntity.internalServerError().body(new ApiResponseError("US0001", "data empty"));
            }
        } catch (Exception ex) {
            LOG.error("ERROR => apis/user/checkmail : " + ex);
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body(new ApiResponseError("US0001" +
                    "", "Can not check mail"));
        }
    }

    @RequestMapping(value = "sendmail", method = RequestMethod.POST)
    public ResponseEntity<?> sendmail(@RequestBody String body) {
        JSONObject req = new JSONObject(body);
        try {
            String email = req.getString("email");
            String topic = req.getString("topic");
            String description = req.getString("description");

            if (email != null && topic != null && description != null) {
                // Prepare the email
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
                helper.setTo(email);
                helper.setSubject(topic);
                helper.setText(description, true);
                helper.setFrom("thanjaru@gmail.com");

                // Send the email
                mailSender.send(message);

                LOG.info("SUCCESS => apis/user/sendmail : " + req);
                return ResponseEntity.ok("Email sent successfully");
            } else {
                LOG.error("ERROR => apis/user/sendmail data empty : " + req);
                return ResponseEntity.internalServerError().body(new ApiResponseError("US0001", "Data is empty"));
            }
        } catch (Exception ex) {
            LOG.error("ERROR => apis/user/sendmail : " + ex);
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body(new ApiResponseError("US0001", "Cannot send email"));
        }
    }
}
