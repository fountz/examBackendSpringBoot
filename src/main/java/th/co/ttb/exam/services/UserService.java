package th.co.ttb.exam.services;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import th.co.ttb.exam.entities.User;

public interface UserService {
    DataTablesOutput<User> findAll(DataTablesInput input);
    User save(User user);
    User findById(String uuid);
    boolean findByEmail(String Email);
    User findByEmailAndPassword(String email,String password);
    String delete(String uuid);
}
