package com.ssafy.chuck.log.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.chuck.log.dto.LogDto;

@Mapper
public interface LogDao {

	// 1. 로그 생성
	int create(LogDto log);

	// 2. 로그 전체 조회
	List<LogDto> readAll(int id);
}
