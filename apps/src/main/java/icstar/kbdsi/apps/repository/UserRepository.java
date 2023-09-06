package icstar.kbdsi.apps.repository;

import icstar.kbdsi.apps.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
//    List<User> findByUserNameContaining(String name);
//    List<User> findByUserEmailContaining(String email);
//    List<User> findByUserPhoneContaining(String phone);
//

}
