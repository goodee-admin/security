package com.example.app.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.dto.User;

@Mapper
public interface UserMapper {
	int insertUser(User user);
	User selectUserByname(String username);
}
