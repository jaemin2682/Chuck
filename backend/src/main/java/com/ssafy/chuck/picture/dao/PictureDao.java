package com.ssafy.chuck.picture.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.chuck.picture.dto.PictureDto;

@Mapper
public interface PictureDao {
	public int insertPicture(int diary_id, String path);
	public int deletePictureByPath(String path);
	public int deletePictureById(int id);
	public List<PictureDto> selectPictureByDiaryId(int diary_id);
	public int deletePictureByDiaryId(int diary_id);
	public Object selectDiaryIdByPath(String path);
}
