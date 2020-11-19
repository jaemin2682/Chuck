package com.ssafy.chuck.reply.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.ssafy.chuck.common.annotation.ReplyLog;
import com.ssafy.chuck.reply.dao.ReplyDao;
import com.ssafy.chuck.reply.dto.ReplyDto;

@Service
public class ReplyServiceImpl implements ReplyService {

	@Autowired
	private ReplyDao replyDao;

	@ReplyLog
	@Override
	public void insertComment(ReplyDto reply, int num) {
		Date date = new Date(System.currentTimeMillis());
		reply.setDate(date);
		replyDao.insertComment(reply);
	}

	@Override
	public List<ReplyDto> selectCommentByDiaryId(int diaryId) {
		return replyDao.selectCommentByDiaryId(diaryId);
	}

	@Override
	public List<ReplyDto> selectCommentByWriter(Long writerId) {
		return replyDao.selectCommentByWriter(writerId);
	}

	@Override
	public void delete(int id) {
		try {
			replyDao.delete(id);
		} catch (DataAccessException e) {
			throw e;
		}
	}
}
