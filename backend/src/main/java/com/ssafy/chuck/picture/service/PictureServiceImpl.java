package com.ssafy.chuck.picture.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.chuck.picture.dao.PictureDao;
import com.ssafy.chuck.picture.dto.PictureDto;

@Service
public class PictureServiceImpl implements PictureService{
	@Autowired
	private PictureDao pictureDao;
	
	@Override
	public int insertPicture(int diary_id, String path) {
		return pictureDao.insertPicture(diary_id, path);
	}

	@Override
	public int deletePictureByPath(String path) {
		return pictureDao.deletePictureByPath(path);
	}

	@Override
	public int deletePictureById(int id) {
		return pictureDao.deletePictureById(id);
	}

	@Override
	public List<PictureDto> selectPictureByDiaryId(int diary_id) {
		return pictureDao.selectPictureByDiaryId(diary_id);
	}

	@Override
	public int deletePictureByDiaryId(int diary_id) {
		return pictureDao.deletePictureByDiaryId(diary_id);
	}

	@Override
	public Object selectDiaryIdByPath(String path) {
		return pictureDao.selectDiaryIdByPath(path);
	}
	
}
