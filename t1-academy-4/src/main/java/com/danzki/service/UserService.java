package com.danzki.service;

import com.danzki.dao.UserDao;
import com.danzki.model.User;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User add(String userName) {
        User user = new User(null, userName);
        try {
            return userDao.add(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User update(Long id, String newUsername) {
        User user;
        try {
            user = userDao.findById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (user != null) {
            try {
                user.setUserName(newUsername);
                return userDao.update(user);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public Boolean delete(User user) {
        try {
            return userDao.delete(user.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User find(User user) {
        try {
            return userDao.findById(user.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> findAll() {
        try {
            return userDao.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
