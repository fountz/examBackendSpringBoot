package th.co.ttb.exam.services;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import th.co.ttb.exam.entities.ActivityLogs;

public interface ActivityLogsService {
    DataTablesOutput<ActivityLogs> findAll(DataTablesInput input);
    ActivityLogs save(ActivityLogs activityLogs);
}
