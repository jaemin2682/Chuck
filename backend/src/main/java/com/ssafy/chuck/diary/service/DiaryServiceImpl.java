package com.ssafy.chuck.diary.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.ssafy.chuck.common.annotation.DiaryLog;
import com.ssafy.chuck.common.annotation.GroupMemberCheck;
import com.ssafy.chuck.diary.dao.DiaryDao;
import com.ssafy.chuck.diary.dto.DiaryDto;
import com.ssafy.chuck.error.exception.AccessDeniedException;

@Service
public class DiaryServiceImpl implements DiaryService{

	@Autowired
	DiaryDao dao;

	@DiaryLog
	@GroupMemberCheck
	@Override
	public void create(long userId, int num, DiaryDto diary) {
		try {
			dao.create(diary);
		} catch (DataAccessException e) {
			throw e;
		}
	}

	@Override
	public DiaryDto read(int id) {
		return dao.read(id);
	}

	@Override
	public void update(long userId, DiaryDto diary) {
		if(diary.getWriterId() != userId) throw new AccessDeniedException("작성자가 아닙니다");
		try {
			dao.update(diary);
		} catch (DataAccessException e) {
			throw e;
		}
	}

	@Override
	public void delete(long userId, int id) {
		if(userId != this.read(id).getWriterId()) throw new AccessDeniedException("작성자가 아닙니다");
		try {
			dao.delete(id);
		} catch (DataAccessException e) {
			throw e;
		}
	}

	@Override
	public List<DiaryDto> readAll(int id) {
		return dao.readAll(id);
	}

	@Override
	public List<DiaryDto> search(int id, String word) {
		return dao.search(id, word);
	}
}
