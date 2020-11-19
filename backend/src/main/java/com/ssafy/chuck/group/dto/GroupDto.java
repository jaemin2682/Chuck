package com.ssafy.chuck.group.dto;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

public class GroupDto {

	@ApiModelProperty(value = "그룹 아이디", example = "1")
	private int id;

	@ApiModelProperty(value = "그룹장 아이디", example = "123456789")
	private long userId;

	@ApiModelProperty(value = "그룹 이름", example = "쓰는척")
	private String name;

	@ApiModelProperty(value = "그룹 소개", example = "안녕하세요 척의 개발그룹 쓰는척입니다.")
	private String intro;

	@ApiModelProperty(value = "그룹 토큰", example = "비밀")
	private String token;

	@ApiModelProperty(value = "그룹 생성 일자", example = "2020-11-03")
	private Date publishedDate;

	@ApiModelProperty(value = "그룹장 이름", example = "김김진진우우")
	private String ownerName;

	public GroupDto() {}

	public GroupDto(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	@Override
	public String toString() {
		return "GroupDto{" +
			"id=" + id +
			", userId=" + userId +
			", name='" + name + '\'' +
			", intro='" + intro + '\'' +
			", token='" + token + '\'' +
			", publishedDate=" + publishedDate +
			", ownerName='" + ownerName + '\'' +
			'}';
	}
}
