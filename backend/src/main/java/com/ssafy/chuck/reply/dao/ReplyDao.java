package com.ssafy.chuck.reply.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.chuck.reply.dto.ReplyDto;

@Mapper
public interface ReplyDao {
	int insertComment(ReplyDto reply);
	ReplyDto read(int id);
	List<ReplyDto> selectCommentByDiaryId(int diaryId);
	List<ReplyDto> selectCommentByWriter(Long writerId);
	int delete(int id);
}
