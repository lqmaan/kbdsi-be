package icstar.kbdsi.apps.services;

import icstar.kbdsi.apps.models.User;
import icstar.kbdsi.apps.repository.UserRepository;
import org.springframework.stereotype.Service;
import icstar.kbdsi.apps.services.UserService;

import java.util.List;


@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        super();
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }
}
