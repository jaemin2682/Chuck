package com.ssafy.chuck.log.service;

import java.util.List;

import com.ssafy.chuck.log.dto.LogDto;

public interface LogService {

	// 로그 생성
	void create(LogDto log);

	// 그룹내 로그 전체 조회
	List<LogDto> readAll(int id);

}
