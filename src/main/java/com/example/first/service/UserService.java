package com.example.first.service;

import com.example.first.bean.User;
public interface UserService {
    User selectUserById(Integer userId);
}