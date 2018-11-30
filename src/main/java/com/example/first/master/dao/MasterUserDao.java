package com.example.first.dao;

import com.example.first.bean.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {
    public User selectUserById(Integer userId);
    public List<User> findUserAll(User user);
}