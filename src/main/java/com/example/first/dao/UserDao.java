package com.example.first.dao;

import com.example.first.bean.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    public User selectUserById(Integer userId);
}