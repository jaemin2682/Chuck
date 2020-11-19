package com.ssafy.chuck.picture.service;

import java.util.List;

import com.ssafy.chuck.picture.dto.PictureDto;

public interface PictureService {
	public int insertPicture(int diary_id, String path);
	public int deletePictureByPath(String path);
	public int deletePictureById(int id);
	public List<PictureDto> selectPictureByDiaryId(int diary_id);
	public int deletePictureByDiaryId(int diary_id);
	public Object selectDiaryIdByPath(String path);
}
