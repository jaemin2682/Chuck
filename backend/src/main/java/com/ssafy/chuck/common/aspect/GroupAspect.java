package com.ssafy.chuck.common.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ssafy.chuck.diary.dto.DiaryDto;
import com.ssafy.chuck.diary.service.DiaryService;
import com.ssafy.chuck.error.exception.AccessDeniedException;
import com.ssafy.chuck.group.dto.GroupDto;
import com.ssafy.chuck.group.service.GroupService;

@Component
@Aspect
public class GroupAspect {

	private static final Logger logger = LoggerFactory.getLogger(GroupAspect.class);

	@Autowired
	GroupService service;

	@Autowired
	DiaryService dirayService;

	@Before("@annotation(com.ssafy.chuck.common.annotation.GroupOwnerCheck)")
	private void checkOwner(JoinPoint point) {
		logger.debug("그룹장 체크");
		Object[] parameterValues = point.getArgs();
		GroupDto dto = (GroupDto)parameterValues[0];
		long userId = (long)parameterValues[1];
		long originUserId = service.readOwner(dto.getId());
		if(userId == originUserId) {
			if(service.readAllMember(userId, 0, dto.getId()).size() != 1) {
				throw new AccessDeniedException("그룹내 멤버가 아직 있습니다");
			}
		} else {
			throw new AccessDeniedException("그룹장 확인 필요");
		}
	}

	@Before("@annotation(com.ssafy.chuck.common.annotation.GroupMemberCheck)")
	private void checkUser(JoinPoint point) {
		logger.debug("그룹 접근 권한 체크");
		Object[] parameterValues = point.getArgs();
		if((int)parameterValues[1] == 0) {
			// 그룹 조회
			long userId = (long)parameterValues[0];
			int id = (int)parameterValues[2];
			if(!service.isMember(userId, id)) {
				throw new AccessDeniedException("그룹 멤버가 아닙니다.");
			}
		} else if((int)parameterValues[1] == 1){
			// 다이어리 관련
			long userId = (long)parameterValues[0];
			DiaryDto dto = (DiaryDto)parameterValues[2];
			if(!service.isMember(userId, dto.getGroupId())) {
				throw new AccessDeniedException("그룹 멤버가 아닙니다.");
			}
		} else {
			// 댓글 관련
			long userId = (long)parameterValues[0];
			DiaryDto dto = dirayService.read((int)parameterValues[3]);
			if(!service.isMember(userId, dto.getGroupId())) {
				throw new AccessDeniedException("그룹 멤버가 아닙니다.");
			}
		}
	}
}
