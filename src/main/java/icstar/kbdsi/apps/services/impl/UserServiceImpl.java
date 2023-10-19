package icstar.kbdsi.apps.services.impl;

import icstar.kbdsi.apps.models.User;
import icstar.kbdsi.apps.repository.UserRepository;
import icstar.kbdsi.apps.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        super();
        this.userRepository = userRepository;
    }

    public Page<User> findUsersContainingName(String name, boolean isDeleted, Integer pageNum,Integer pageSize){
        Page<User> users = userRepository.findByNameContainingAndDeleted(name, isDeleted,  PageRequest.of(pageNum, pageSize), Sort.by("name").descending().by("createdAt"));
        return  users;
    }
    @Override
    public List<User> getAllUsers() {
        return userRepository.findByDeleted(false);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmailAndDeleted(email, false);
    }
    @Override
    public User getUserByName(String name) {
        return userRepository.findByNameAndDeleted(name, false);
    }

    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException{
        User user = userRepository.findByEmailAndDeleted(email, false);
    if(user == null){
        throw new UsernameNotFoundException("User Email not exists, Please register");
    }
    return new org.springframework.security.core.userdetails.User(email, user.getPassword(),null);
    }

    @Override
    public ResponseEntity<User> updateUserById(User newUser, Long id){
        Optional<User> optUser = userRepository.findById(id);
        if(optUser.isPresent()) {
            User oldUser = optUser.get();
            oldUser.setName(newUser.getName());
            oldUser.setPhone(newUser.getPhone());
            oldUser.setEmail(newUser.getEmail());
            String pw = new BCryptPasswordEncoder().encode(newUser.getPassword());
            oldUser.setPassword(pw);
            oldUser.setRoles(newUser.getRoles());
            oldUser.setUpdatedBy(newUser.getUpdatedBy());
            userRepository.save(oldUser);
        return new ResponseEntity<>(oldUser, HttpStatus.OK);
    }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
