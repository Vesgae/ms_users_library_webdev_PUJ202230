package com.web202230.ms_users.controller;

import com.web202230.ms_users.dto.Mensaje;
import com.web202230.ms_users.entity.User;
import com.web202230.ms_users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    private List<User> getAllUsers() {
        return userService.getUsers();
    }

    private User getUserByMail(String email) {
        List<User> users = getAllUsers();
        for(User user: users){
            if(user.getEmail().equals(email))
                return user;
        }
        return null;
    }
    private Long getUserId(String email){
        List<User> users = getAllUsers();
        for(User user: users){
            if(user.getEmail().equals(email))
                return (long) users.indexOf(user);
        }
        return (long) -1;

    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("users/{email}")
    public ResponseEntity<User> getUser(@PathVariable String email) {
        User currentUser = getUserByMail(email);
        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
    }

    @DeleteMapping("/users")
    public ResponseEntity<?> deleteUser(@RequestParam String email) {
        Long id = getUserId(email);
        if (!userService.existsByIdUser(id))
            return new ResponseEntity(new Mensaje("No existe el usuario"), HttpStatus.NOT_FOUND);
        userService.deleteUser(id);
        return new ResponseEntity(new Mensaje("Usuario eliminado"), HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity updateUser(@PathVariable String email, @RequestBody User user) {
        Long id = getUserId(email);
        User currentUser = userService.getUser(id).get();
        currentUser.setName(user.getName());
        currentUser.setBirthday(user.getBirthday());
        userService.saveUser(currentUser);
        return new ResponseEntity(new Mensaje("Usuario Actualizado"), HttpStatus.OK);
    }
}