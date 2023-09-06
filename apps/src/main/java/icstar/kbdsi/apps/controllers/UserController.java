package icstar.kbdsi.apps.controllers;

import icstar.kbdsi.apps.models.User;
import icstar.kbdsi.apps.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers (@RequestParam(required = false) String name){
        try{
            List<User> users = new ArrayList<User>();

            if(name == null)
                userRepository.findAll().forEach((users::add));
//            else
//                userRepository.findByUserNameContaining(name).forEach(users::add);
            if(users.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user){
        try{
            User userNew = userRepository
                    .save(new User(user.getName(), user.getEmail(), user.getPhone(), user.getPassword()));
            return new ResponseEntity<>(userNew, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
