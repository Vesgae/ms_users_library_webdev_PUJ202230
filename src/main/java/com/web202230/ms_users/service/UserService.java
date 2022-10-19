package com.web202230.ms_users.service;

import com.web202230.ms_users.entity.User;
import com.web202230.ms_users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public List<User> getUsers(){
        return userRepository.findAll();
    }
    public Optional<User> getUser(Long id){
        return userRepository.findById(id);
    }
    public boolean existsByIdUser(Long id){
        return userRepository.existsById(id);
    }
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
    public void saveUser(User user){
        userRepository.save(user);
    }
}