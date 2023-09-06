package icstar.kbdsi.apps.services;

import icstar.kbdsi.apps.models.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User createUser(User user);
}
