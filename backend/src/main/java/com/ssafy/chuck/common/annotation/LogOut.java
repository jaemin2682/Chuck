package com.ssafy.chuck.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogOut {
	/**
	 * 로그아웃 (카카오상에서도 로그아웃처리)
	 */
}
