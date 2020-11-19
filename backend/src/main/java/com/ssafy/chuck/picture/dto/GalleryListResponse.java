package com.ssafy.chuck.picture.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GalleryListResponse {
	private List<GalleryResponse> gallery_list;
}
