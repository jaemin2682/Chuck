package com.ssafy.chuck.music.dto;

import io.swagger.annotations.ApiModelProperty;

public class MusicDto {

	@ApiModelProperty(value = "음악 아이디", example = "1")
	private int id;

	@ApiModelProperty(value = "음악 이름", example = "Don't Waste My Time")
	private String name;

	@ApiModelProperty(value = "앨범 커버", example = "Don't Waste My Time (Instrumental Version).png")
	private String albumCover;

	@ApiModelProperty(value = "장르", example = "Acoustic")
	private String genre;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlbumCover() {
		return albumCover;
	}

	public void setAlbumCover(String albumCover) {
		this.albumCover = albumCover;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	@Override
	public String toString() {
		return "MusicDto{" +
			"id=" + id +
			", name='" + name + '\'' +
			", albumCover='" + albumCover + '\'' +
			", genre='" + genre + '\'' +
			'}';
	}
}
