package com.example.security.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.security.dto.User;

@Mapper
public interface UserMapper {
	int insertUser(User user);
	User selectUserByName(String username);
}
