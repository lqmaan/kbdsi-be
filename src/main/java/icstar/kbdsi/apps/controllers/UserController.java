package icstar.kbdsi.apps.controllers;

import icstar.kbdsi.apps.dto.APIResponse;
import icstar.kbdsi.apps.dto.LoginDto;
import icstar.kbdsi.apps.dto.UserDetailsDto;
import icstar.kbdsi.apps.dto.UserDto;
import icstar.kbdsi.apps.models.User;
import icstar.kbdsi.apps.repository.UserRepository;
import icstar.kbdsi.apps.services.UserService;
import icstar.kbdsi.apps.util.ExcelGenerator;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

//@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserRepository userRepository;
    UserService userService;

    public UserController(UserService userService) {
        super();
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers (@RequestParam(required = false) String name){
        try{
            List<User> users = new ArrayList<User>();

            if(name == null)
//                userRepository.findAll().forEach((users::add));
                userService.getAllUsers().forEach(users::add);
//            else
//                userRepository.findAllByName(name, PageRequest.of(0, 10, Sort.by("name").ascending()));
            if(users.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/auth")
    public String authUser (@RequestBody String email, String password){
            boolean res = userService.authUser(email, password);
            if(res){
                return "Login Success";
            }
            else{
                return "Login Failed";
            }

    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user){
        try{
            String pw = new BCryptPasswordEncoder().encode(user.getPassword());
            user.setPassword(pw);
            System.out.println(user);
            userRepository.save(new User(user.getName(), user.getEmail(), user.getPhone(), user.getPassword() , user.getRoles()));
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUserById(@PathVariable(required = true) Long id, @RequestBody User newUser) {
       return userService.updateUserById(newUser, id);
    }

    @GetMapping("/users/export-to-excel")
    public void exportIntoExcelFile(HttpServletResponse response) throws IOException{
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue= "attachment; filename=user" + currentDateTime + ".xlsx";
        response.setHeader(headerKey,headerValue);

        List<User> listOfUsers = userRepository.findAll();
//        List<User> listOfUsers = userService.getAllUsers();
        ExcelGenerator generator = new ExcelGenerator(listOfUsers);
        generator.generateExcelFile(response);
    }

    @PostMapping("/login")
    public UserDetailsDto loginUser(@RequestBody LoginDto user){
        User loginUser = userService.getUserByEmail(user.getEmail());
        UserDetailsDto res =  new UserDetailsDto();
        if(loginUser != null){
            boolean isMatch = new BCryptPasswordEncoder().matches(user.getPassword(), loginUser.getPassword());
            if(isMatch){
                res.setId(loginUser.getId());
                res.setName(loginUser.getName());
                res.setEmail(loginUser.getEmail());
                res.setPhone(loginUser.getPhone());
                res.setRoles(loginUser.getRoles());
                return res;
            }
            else{
                return new UserDetailsDto();
            }
        }
        else{
            return new UserDetailsDto();
        }
    }

//    @GetMapping("/users/page")
//    private Page<User> getUsersWithPaginationAndSort(@RequestBody UserDto userDto) {
//        Page<User> users = userService.findUsersWithPagination(userDto.getPageNum(), userDto.getPageSize());
//        return users;
//    }

    @GetMapping("/users/name")
    private Page<User> getUsersWithFilterName(@RequestBody UserDto userDto) {
        Page<User> users = userService.findUsersContainingName(userDto.getName(), userDto.getPageNum(), userDto.getPageSize());
        return users;
    }
//    @GetMapping("/users/user")
//    public ResponseEntity<Map<String, Object>> getAllUsersPaging(@RequestParam(required = false) String roles, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
//      try{
//          List<User> users = new ArrayList<User>();
//          Pageable paging = (Pageable) PageRequest.of(page, size);
//          Page<User> pageUsers;
//          if(roles ==  null)
//              pageUsers = userRepository.findAll(paging);
//          else
//              pageUsers = userRepository.findByRolesContaining(roles, paging);
//
//          users = pageUsers.getContent();
//          Map<String, Object> response = new HashMap<>();
//          response.put("users", users);
//          response.put("currentPage", pageUsers.getNumber());
//          response.put("totalItems", pageUsers.getTotalElements());
//          response.put("totalPages", pageUsers.getTotalPages());
//
//          return new ResponseEntity<>(response, HttpStatus.OK);
//      }
//      catch (Exception e){
//          return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//      }
//    }



}
