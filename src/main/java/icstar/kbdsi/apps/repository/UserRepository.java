package icstar.kbdsi.apps.repository;

import icstar.kbdsi.apps.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
//    Page<User> findByRolesContaining(String roles, Pageable pageable);
    Page<User> findByNameContaining(String name, PageRequest pageable, Sort sort);
//    Page<User> findAll(Pageable);
    User findByEmail(String email);
    User findByName(String name);


    void findAllByName(String name, PageRequest pageRequest);


//    List<User> findByUserNameContaining(String name);
//    List<User> findByUserEmailContaining(String email);
//    List<User> findByUserPhoneContaining(String phone);
//

}
