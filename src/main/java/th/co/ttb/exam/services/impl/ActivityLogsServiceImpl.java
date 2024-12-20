package th.co.ttb.exam.services.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;
import th.co.ttb.exam.entities.ActivityLogs;
import th.co.ttb.exam.repositories.ActivityLogsRepository;
import th.co.ttb.exam.services.ActivityLogsService;

import java.util.Date;

@Service
public class ActivityLogsServiceImpl implements ActivityLogsService {
    private final static Logger LOG = LogManager.getLogger(ActivityLogsServiceImpl.class);
    @Autowired
    ActivityLogsRepository activityLogsRepository;


    @Override
    public ActivityLogs save(ActivityLogs activityLogs) {
        activityLogs.setCreatedAt(new Date());
        activityLogs = activityLogsRepository.save(activityLogs);
        return activityLogs;
    }

    @Override
    public DataTablesOutput<ActivityLogs> findAll(DataTablesInput input) {
        return activityLogsRepository.findAll(input);
    }

}
