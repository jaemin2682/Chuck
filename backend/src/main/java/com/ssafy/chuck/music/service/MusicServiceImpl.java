package com.ssafy.chuck.music.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.chuck.music.dao.MusicDao;
import com.ssafy.chuck.music.dto.MusicDto;

@Service
public class MusicServiceImpl implements MusicService{

	@Autowired
	MusicDao dao;

	@Override
	public List<MusicDto> readAll() {
		return dao.readAll();
	}
}
