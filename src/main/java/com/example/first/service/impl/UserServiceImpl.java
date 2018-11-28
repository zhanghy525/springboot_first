package com.example.first.service.impl;

import com.example.first.bean.User;
import com.example.first.dao.UserDao;
import com.example.first.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public User selectUserById(Integer userId){
        return userDao.selectUserById(userId);
    }
}
