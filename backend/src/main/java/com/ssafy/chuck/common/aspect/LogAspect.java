package com.ssafy.chuck.common.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ssafy.chuck.diary.dto.DiaryDto;
import com.ssafy.chuck.diary.service.DiaryService;
import com.ssafy.chuck.group.dto.GroupDto;
import com.ssafy.chuck.group.dto.MemberDto;
import com.ssafy.chuck.log.dto.LogDto;
import com.ssafy.chuck.log.service.LogService;
import com.ssafy.chuck.reply.dto.ReplyDto;

@Aspect
@Component
public class LogAspect {

	private static Logger logger = LoggerFactory.getLogger(LogAspect.class);

	@Autowired
	LogService service;

	@Autowired
	DiaryService diaryService;

	@AfterReturning("@annotation(com.ssafy.chuck.common.annotation.JoinLog)")
	private void joinLog(JoinPoint point) {
		logger.debug("그룹 조인 로그");
		Object[] parameterValues = point.getArgs();
		MemberDto dto = (MemberDto)parameterValues[0];
		String comment = dto.getUserId() + "님이 그룹에 가입되었습니다.";
		service.create(new LogDto(dto.getGroupId(), comment));
	}

	@AfterReturning("@annotation(com.ssafy.chuck.common.annotation.DiaryLog)")
	private void diaryLog(JoinPoint point) {
		logger.debug("그룹내 일기 작성 로그");
		Object[] parameterValues = point.getArgs();
		long userId = (long)parameterValues[0];
		DiaryDto dto = (DiaryDto)parameterValues[2];
		String comment = userId + "님이 새로운 Chuck(" + dto.getId() + ")을 게시하였습니다.";
		service.create(new LogDto(dto.getGroupId(), comment));
	}

	@AfterReturning("@annotation(com.ssafy.chuck.common.annotation.ReplyLog)")
	private void replyLog(JoinPoint point) {
		logger.debug("그룹내 댓글 작성 로그");
		Object[] parameterValues = point.getArgs();
		ReplyDto dto = (ReplyDto)parameterValues[0];
		long userId = dto.getWriterId();
		int diaryId = dto.getDiaryId();
		DiaryDto diary = diaryService.read(diaryId);
		String comment = userId + "님이 새로운 댓글(" + diaryId + ")을 게시하였습니다.";
		service.create(new LogDto(diary.getGroupId(), comment));
	}

	@AfterReturning("@annotation(com.ssafy.chuck.common.annotation.SignOutLog)")
	private void signOutLog(JoinPoint point) {
		logger.debug("그룹 탈퇴 로그");
		Object[] parameterValues = point.getArgs();
		int id = (int)parameterValues[0];
		long userId = (long)parameterValues[1];
		long requestId = (long)parameterValues[2];
		String comment = "";
		if(userId == requestId) {
			comment = userId + "님이 그룹을 탈퇴하였습니다.";
		} else {
			comment = userId + "님이 그룹에서 추방되었습니다.";
		}
		service.create(new LogDto(id, comment));
	}

	@AfterReturning("@annotation(com.ssafy.chuck.common.annotation.ChangeLog)")
	private void changeLog(JoinPoint point) {
		logger.debug("그룹장 변화 로그");
		Object[] parameterValues = point.getArgs();
		long userId = (long)parameterValues[3];
		GroupDto dto = (GroupDto)parameterValues[4];
		String comment = userId + "님이 새로운 그룹장으로 변경되었습니다.";
		service.create(new LogDto(dto.getId(), comment));
	}
}
