package com.ssafy.chuck.music.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.chuck.music.dto.MusicDto;

@Mapper
public interface MusicDao {

	// 1. 음악 전체 리스트 조회
	List<MusicDto> readAll();
}
