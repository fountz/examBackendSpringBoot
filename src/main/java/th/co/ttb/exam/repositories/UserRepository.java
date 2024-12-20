package th.co.ttb.exam.repositories;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import th.co.ttb.exam.entities.User;

import java.util.Optional;

public interface UserRepository extends DataTablesRepository<User, String> {
    Optional<User> findLastByEmailAndPassword(String email,String password);
    Optional<User> findLastByEmail(String email);
}
