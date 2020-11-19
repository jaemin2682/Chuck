package com.ssafy.chuck.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SignOut {
	/**
	 * 회원탈퇴 (카카오 연결끊기)
	 */
}
