package com.ssafy.chuck.music.service;

import java.util.List;

import com.ssafy.chuck.music.dto.MusicDto;

public interface MusicService {

	// 음악 전체 리스트 조회
	List<MusicDto> readAll();
}
