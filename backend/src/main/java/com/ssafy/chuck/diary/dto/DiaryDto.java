package com.ssafy.chuck.diary.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;

public class DiaryDto {

	@ApiModelProperty(value = "다이어리 아이디", example = "1")
	private int id;

	@ApiModelProperty(value = "제목", example = "하하하하")
	private String title;

	@ApiModelProperty(value = "이미지", example = "1234-trewq-ewqeq.jpeg")
	private String image;

	@ApiModelProperty(value = "내용", example = "오늘 서울 맑음")
	private String content;

	@ApiModelProperty(value = "글쓴이 이름", example = "123458642")
	private String writer;

	@ApiModelProperty(value = "글쓴이 아이디", example = "123458642")
	private long writerId;

	@ApiModelProperty(value = "그룹 아이디", example = "1")
	private int groupId;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
	@ApiModelProperty(value = "작성일", example = "2020-10-05")
	private Date date;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getWriterId() {
		return writerId;
	}

	public void setWriterId(long writerId) {
		this.writerId = writerId;
	}

	@Override
	public String toString() {
		return "DiaryDto{" +
			"id=" + id +
			", title='" + title + '\'' +
			", image='" + image + '\'' +
			", content='" + content + '\'' +
			", writer=" + writer +
			", writerId=" + writerId +
			", groupId=" + groupId +
			", date=" + date +
			'}';
	}
}
