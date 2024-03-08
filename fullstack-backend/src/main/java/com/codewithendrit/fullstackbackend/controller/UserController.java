package com.codewithendrit.fullstackbackend.controller;

import com.codewithendrit.fullstackbackend.exception.UserNotFoundException;
import com.codewithendrit.fullstackbackend.model.User;
import com.codewithendrit.fullstackbackend.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:3000/")
public class UserController {

    private final UserRepository userRepository;


    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @PostMapping("/user")
    public User postUser(@RequestBody User newUser){
        return userRepository.save(newUser);
    }

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable Long id){

        return userRepository.findById(id).orElseThrow(()-> new UserNotFoundException(id));

    }

    @PutMapping("/user/{id}")
    public User updateUser(@RequestBody User userToUpdate,
                           @PathVariable Long id){

        return userRepository.findById(id).map(user -> {
            user.setUsername(userToUpdate.getUsername());
            user.setName(userToUpdate.getName());
            user.setEmail(userToUpdate.getEmail());
            return userRepository.save(user);
        }).orElseThrow(()-> new UserNotFoundException(id));
    }

    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable Long id){
        if(!userRepository.existsById(id)){
            throw new UserNotFoundException(id);
        }else {
          userRepository.deleteById(id);
          return "User with id: " + id + " was deleted!";
        }

    }

}

