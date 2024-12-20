package th.co.ttb.exam.repositories;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import th.co.ttb.exam.entities.ActivityLogs;

public interface ActivityLogsRepository extends DataTablesRepository<ActivityLogs, String> {
}
