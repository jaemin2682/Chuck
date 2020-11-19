package com.ssafy.chuck.picture.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GalleryResponse {
	private String rep_image;
	private List<Gallery> content_list;
}
