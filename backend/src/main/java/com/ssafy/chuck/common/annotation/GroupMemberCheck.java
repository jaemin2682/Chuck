package com.ssafy.chuck.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface GroupMemberCheck {
	/**
	 * 해당 멤버가 그룹원인지 체크
	 */
}
