package com.ssafy.chuck.diary.service;

import java.util.List;

import com.ssafy.chuck.diary.dto.DiaryDto;

public interface DiaryService {

	// 다이어리 생성
	void create(long userId, int num, DiaryDto diary);

	// 다이어리 상세 조회
	DiaryDto read(int id);

	// 다이어리 수정
	void update(long userId, DiaryDto diary);

	// 다이어리 삭제
	void delete(long userId, int id);

	// 다이어리 리스트 조회
	List<DiaryDto> readAll(int id);

	// 다이어리 검색
	List<DiaryDto> search(int id, String word);
}
