package owl.medassist_back.userService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import owl.medassist_back.userService.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByLogin(String login);

    boolean existsByLogin(String login);

}
