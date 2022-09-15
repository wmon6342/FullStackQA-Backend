package com.example.FullStackQABackend.Controller;


import com.example.FullStackQABackend.Exception.UserNotFoundException;
import com.example.FullStackQABackend.Model.User;
import com.example.FullStackQABackend.Repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@CrossOrigin("http://localhost:3000")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // for posting data
    @PostMapping("/users")
    User newUser (@RequestBody User newUser) {
        return userRepository.save(newUser);
    }

    // for getting data

    @GetMapping("/users")
    List<User> all() {
        return userRepository.findAll();
    }

    //get user information with id
    @GetMapping("/user/{id}")
    User getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException(id));
    }

    // For updating the user information
    @PutMapping("/user/{id}")
    User updateUser(@RequestBody User newUser, @PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setName(newUser.getName());
                    user.setUsername(newUser.getUsername());
                    user.setEmail(newUser.getEmail());
                    return userRepository.save(user);

                }).orElseGet(()->{
                    newUser.setId(id);
                    return userRepository.save(newUser);
                });
    }

    //delete the user information with id
    @DeleteMapping("/user/{id}")
    String deleteUser(@PathVariable Long id) {
      if(!userRepository.existsById(id)) {
          throw new UserNotFoundException(id);
      }
      userRepository.deleteById(id);
      return "User with id " + id + " deleted successfully.";
    }

}
