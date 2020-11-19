package com.ssafy.chuck.common.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ssafy.chuck.common.PermissionCheck;
import com.ssafy.chuck.error.exception.AccessDeniedException;

@Aspect
@Component
public class PermissionAspect {
	private static final Logger logger = LoggerFactory.getLogger(PermissionCheck.class);

	@Autowired
	PermissionCheck permissionCheck;

	@Pointcut("execution(void com.ssafy.chuck.user.service.*.logout(..))")
	private void isRight() {}


	@Before("@annotation(com.ssafy.chuck.common.annotation.PermissionChecking)")
	private void UserPermissionCheck(JoinPoint point) {
		logger.debug("사용자 권한 체크");
		Object[] parameterValues = point.getArgs();
		long userId = (Long)parameterValues[1];
		String token = String.valueOf(parameterValues[0]);

		if(userId != permissionCheck.check(token).getId()) {
			throw new AccessDeniedException("사용자 정보 불일치");
		}
	}

	@After("isRight()")
	private void LogoutUserCheck(JoinPoint point) {
		logger.debug("사용자 권한 체크");
		Object[] parameterValues = point.getArgs();
		long userId = (Long)parameterValues[1];
		String token = String.valueOf(parameterValues[0]);

		if(userId != permissionCheck.check(token).getId()) {
			throw new AccessDeniedException("사용자 정보 불일치");
		}
	}
}
