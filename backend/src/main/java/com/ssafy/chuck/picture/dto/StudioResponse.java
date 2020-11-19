package com.ssafy.chuck.picture.dto;

import java.util.List;

import com.ssafy.chuck.diary.dto.DiaryDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudioResponse {
	private String rep_image;
	private List<DiaryDto> content_list;
}
