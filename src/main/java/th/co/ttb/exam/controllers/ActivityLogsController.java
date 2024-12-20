package th.co.ttb.exam.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import th.co.ttb.exam.entities.ActivityLogs;
import th.co.ttb.exam.models.ApiResponseError;
import th.co.ttb.exam.services.ActivityLogsService;

@RestController
@RequestMapping("/apis/logs")
public class ActivityLogsController {
    private final static Logger LOG = LogManager.getLogger(ActivityLogsController.class);
    @Autowired
    ActivityLogsService activityLogsService;

    @Autowired
    ObjectMapper objectMapper;

    @RequestMapping(value = "datatable", method = RequestMethod.POST)
    public DataTablesOutput<ActivityLogs> getListForDataTables(@RequestBody DataTablesInput input) {
        try {
            LOG.info("SUCCESS => apis/logs/datatable : " + input);
            return activityLogsService.findAll(input);
        } catch (Exception ex) {
            LOG.error("ERROR => apis/logs/datatable: ", ex);
            // Return an empty DataTablesOutput in case of an error
            DataTablesOutput<ActivityLogs> errorOutput = new DataTablesOutput<>();
            errorOutput.setError("An error occurred while processing your request.");
            return errorOutput;
        }
    }


    @RequestMapping(value = "save", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody ActivityLogs activityLogs) {
        try {
            LOG.info("SUCCESS => apis/logs/save : " + activityLogs);
            return ResponseEntity.ok(activityLogsService.save(activityLogs));
        } catch (Exception ex) {
            LOG.error("ERROR => apis/logs/save : " + activityLogs);
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body(new ApiResponseError("US0001" +
                    "", "Can not save logs"));
        }
    }
}
