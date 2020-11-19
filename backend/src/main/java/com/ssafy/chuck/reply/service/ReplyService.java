package com.ssafy.chuck.reply.service;

import java.util.List;

import com.ssafy.chuck.reply.dto.ReplyDto;

public interface ReplyService {
	void insertComment(ReplyDto reply, int num);
	List<ReplyDto> selectCommentByDiaryId(int diaryId);
	List<ReplyDto> selectCommentByWriter(Long writerId);
	void delete(int id);
}
