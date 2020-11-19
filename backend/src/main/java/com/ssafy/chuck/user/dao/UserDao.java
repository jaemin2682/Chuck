package com.ssafy.chuck.user.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.chuck.user.dto.UserDto;

@Mapper
public interface UserDao {

	// 회원 정보 추가
	int create(UserDto user);

	// 최근 접속 업데이트
	int updateTime(long userId);

	// 회원 정보 조회
	UserDto read(long userId);

	// 회원 정보 수정
	int update(UserDto user);

	// 회원 정보 삭제
	int delete(long userId);
}
