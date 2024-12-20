package th.co.ttb.exam.services.impl;

import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import th.co.ttb.exam.entities.User;
import th.co.ttb.exam.repositories.UserRepository;
import th.co.ttb.exam.services.UserService;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private final static Logger LOG = LogManager.getLogger(UserServiceImpl.class);

    @Autowired
    UserRepository userRepository;


    @Override
    public User save(User user) {
        if (user.getUuid() != null) {
            Optional<User> userOptional = userRepository.findById(user.getUuid());
            if (!userOptional.isPresent()) {
                user.setCreatedAt(new Date());
                user = userRepository.save(user);
            } else {
                User dbUser = userOptional.get();
                dbUser.setEmail(user.getEmail());
                dbUser.setFirstName(user.getFirstName());
                dbUser.setLastName(user.getLastName());
                dbUser.setProfileImg(user.getProfileImg());
                dbUser.setMobileNo(user.getMobileNo());
                dbUser.setBirthDate(user.getBirthDate());
                dbUser.setUpdatedAt(new Date());
//                dbUser.setUpdatedBy(user.getUpdatedBy());
                user = userRepository.save(dbUser);
            }
        } else {
            user = userRepository.save(user);
        }
        return user;
    }

    @Override
    public String delete(String uuid) {
        Optional<User> userOptional = userRepository.findById(uuid);
        if (userOptional.isPresent()) {
            User dbUser = userOptional.get();
            userRepository.delete(dbUser);  // เปลี่ยนจาก remove เป็น delete
            return "success";  // คืนค่าผลลัพธ์หลังจากการลบ
        } else {
            // ถ้าไม่พบข้อมูล
            throw new EntityNotFoundException("User not found with UUID: " + uuid);
        }
    }

    @Override
    public User findById(String uuid) {
        Optional<User> userOptional = userRepository.findById(uuid);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new EntityNotFoundException("User not found with UUID: " + uuid);
        }

    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        Optional<User> userOptional = userRepository.findLastByEmailAndPassword(email, password);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new EntityNotFoundException("User not found with email and password");
        }

    }

    @Override
    public boolean findByEmail(String email) {
        Optional<User> userOptional = userRepository.findLastByEmail(email);
        return userOptional.isEmpty();

    }

    @Override
    public DataTablesOutput<User> findAll(DataTablesInput input) {
        return userRepository.findAll(input);
    }

}
