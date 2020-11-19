package com.ssafy.chuck.picture.dto;

import java.util.List;

import lombok.Data;

@Data
public class PathListResponse {
	private long userId;
	private List<String> path_list;
	private String music;
}
