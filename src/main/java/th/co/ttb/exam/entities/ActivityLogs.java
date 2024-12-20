package th.co.ttb.exam.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity
@Table(name = "T_ACTIVITY_LOGS", schema = "PUBLIC")
@Getter
@Setter
public class ActivityLogs {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String uuid;
    private String email;
    private String userUuid;
    private String ipAddress;
    private String description;
    @CreatedDate
    private Date createdAt;
}
