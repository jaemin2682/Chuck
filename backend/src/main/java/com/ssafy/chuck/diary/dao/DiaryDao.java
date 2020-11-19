package com.ssafy.chuck.diary.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.chuck.diary.dto.DiaryDto;

@Mapper
public interface DiaryDao {

	// 1. 일기 생성
	int create(DiaryDto diary);

	// 2. 일기 상세 조회
	DiaryDto read(int id);

	// 3. 일기 수정
	int update(DiaryDto dto);

	// 4. 일기 삭제
	int delete(int id);

	// 5. 일기 전체 조회
	List<DiaryDto> readAll(int id);

	// 6. 일기 검색
	List<DiaryDto> search(@Param(value = "id") int id, @Param(value = "word") String word);
}
