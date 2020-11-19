package com.ssafy.chuck.picture.dto;

import java.util.List;

import lombok.Data;

@Data
public class PictureResponse {
	private int group_id;
	private int diary_id;
	private List<String> path_list;
}
