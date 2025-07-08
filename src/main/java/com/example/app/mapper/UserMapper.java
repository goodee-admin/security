package com.example.app.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.UserDomain;

@Mapper
public interface UserMapper {
	int insertUser(UserDomain user);
	UserDomain selectUserByname(String username);
}
