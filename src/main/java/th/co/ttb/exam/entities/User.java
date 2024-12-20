package th.co.ttb.exam.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "T_USER", schema = "PUBLIC")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String uuid;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String profileImg;
    private String mobileNo;
    private Date birthDate;
    @CreatedDate
    private Date createdAt;
    private String createdBy;
    @LastModifiedDate
    private Date updatedAt;
    private String updatedBy;
}
