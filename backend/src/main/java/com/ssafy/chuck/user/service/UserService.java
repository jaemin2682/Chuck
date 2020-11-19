package com.ssafy.chuck.user.service;

import com.ssafy.chuck.user.dto.Response;
import com.ssafy.chuck.user.dto.UserDto;

public interface UserService {

	// 로그인
	UserDto login(Response response);

	// 회원 정보 추가 (회원 가입)
	void create(Long userId, String name);

	// 최근 접속 업데이트
	void updateTime(long userId);

	// 회원 정보 조회
	UserDto read(long userId);

	// 회원 정보 수정
	void update(UserDto user);

	// 회원 정보 삭제
	void delete(String token, long userId, String refreshToken);

	// 로그아웃
	void logout(String token, long userId, String refreshToken);
}
