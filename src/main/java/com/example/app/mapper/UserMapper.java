package com.example.app.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.UserDomain;

@Mapper
public interface UserMapper {
	int insertUser(UserDomain userDomain);
	UserDomain selectUserByname(String username);
}
