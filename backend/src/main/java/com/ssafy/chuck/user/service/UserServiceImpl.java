package com.ssafy.chuck.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import com.ssafy.chuck.common.annotation.LogOut;
import com.ssafy.chuck.common.annotation.PermissionChecking;
import com.ssafy.chuck.common.annotation.SignOut;
import com.ssafy.chuck.common.aspect.UserAspect;
import com.ssafy.chuck.error.exception.DuplicateKeyException;
import com.ssafy.chuck.error.exception.EntityNotFoundException;
import com.ssafy.chuck.error.exception.IncorrectFormatException;
import com.ssafy.chuck.user.dao.UserDao;
import com.ssafy.chuck.user.dto.Response;
import com.ssafy.chuck.user.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {

	static final String duplicateKey = "duplicate";
	static final String invalid = "can't find";

	@Autowired
	UserDao dao;

	@Override
	public UserDto login(Response response) {
		Response userInfo = UserAspect.getUserInfo(response.getAccessToken());

		long userId = Long.parseLong(userInfo.getId());
		String name = userInfo.getData().get("nickname");

		UserDto user = this.read(userId);
		if(user != null) {
			this.update(user);
		} else {
			user = new UserDto(userId, name);
			this.create(userId, name);
		}
		return user;
	}

	@Override
	public void create(Long userId, String name) {
		try {
			dao.create(new UserDto(userId, name));
		} catch (DataAccessException e) {
			if (e.getMessage().contains(duplicateKey)) {
				throw new DuplicateKeyException(String.valueOf(userId));
			} else {
				throw new IncorrectFormatException("IncorrectFormatError");
			}
		}
	}

	@Override
	public void updateTime(long userId) {
		try {
			dao.updateTime(userId);
		} catch (DataAccessException e) {
			if(e.getMessage().contains(invalid)) {
				throw new EntityNotFoundException(String.valueOf(userId));
			} else {
				throw new IncorrectFormatException("IncorrectFormatError");
			}
		}
	}

	@Override
	public UserDto read(long userId) {
		UserDto user = new UserDto();
		try {
			user = dao.read(userId);
		} catch (DataAccessException e) {
			if(e.getMessage().contains(invalid)) {
				throw new EntityNotFoundException(String.valueOf(userId));
			} else {
				throw e;
			}
		}
		return user;
	}

	@Override
	public void update(UserDto user) {
		try {
			dao.update(user);
		} catch (DataAccessException e) {
			if(e.getMessage().contains(invalid)) {
				throw new EntityNotFoundException(String.valueOf(user.getId()));
			} else {
				throw e;
			}
		}
	}

	@SignOut
	@PermissionChecking
	@Override
	public void delete(String token, long userId, String refreshToken) {
		try {
			dao.delete(userId);
		} catch (DataAccessException e) {
			if(e.getMessage().contains(invalid)) {
				throw new EntityNotFoundException(String.valueOf(userId));
			} else {
				throw e;
			}
		}
	}

	@LogOut
	@Override
	public void logout(String token, long userId, String refreshToken) {}
}
