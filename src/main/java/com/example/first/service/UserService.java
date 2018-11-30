package com.example.first.service;

import com.example.first.bean.User;

import java.util.List;

public interface UserService {
    User selectUserById(Integer userId);
    List<User> findUserAll(User user);
}