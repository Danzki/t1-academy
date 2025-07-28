package com.danzki.service;

import com.danzki.model.User;
import com.danzki.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;

    public User add(String username) {
        User product = new User();
        product.setUsername(username);
        return userRepo.save(product);
    }

    public User update(Long id, String username) {
        Optional<User> userOpt = userRepo.findById(id);
        if (userOpt.isEmpty()) {
            return null;
        }
        User user = userOpt.get();
        user.setUsername(username);
        return userRepo.save(user);
    }

    public Boolean delete(User user) {
        userRepo.delete(user);
        return true;
    }

    public User find(Long id) {
        Optional<User> userOpt = userRepo.findById(id);
        return userOpt.orElse(null);
    }

    public User findByName(String username) {
        Optional<User> userOpt = userRepo.findByUsername(username);
        return userOpt.orElse(null);
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }
}
