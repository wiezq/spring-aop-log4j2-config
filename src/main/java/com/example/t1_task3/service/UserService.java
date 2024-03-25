package com.example.t1_task3.service;

import com.example.t1_task3.model.UserEntity;
import com.example.t1_task3.repository.UserEntityRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private final UserEntityRepository userRepository;


    public UserEntity getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("User with id " + id + " not found"));
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity createUser(String name, String email) {
        UserEntity user = new UserEntity();
        user.setName(name);
        user.setEmail(email);
        return userRepository.save(user);
    }

    public UserEntity updateUser(Long id, String name, String email) {
        UserEntity user = getUserById(id);
        user.setName(name);
        user.setEmail(email);
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        UserEntity user = getUserById(id);
        userRepository.delete(user);
    }


}
