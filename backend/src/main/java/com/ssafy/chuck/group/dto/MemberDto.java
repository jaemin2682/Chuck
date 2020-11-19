package com.ssafy.chuck.group.dto;

import io.swagger.annotations.ApiModelProperty;

public class MemberDto {

	@ApiModelProperty(value = "그룹 아이디", example = "1")
	private int groupId;

	@ApiModelProperty(value = "추가될 멤버 아이디", example = "123456789")
	private long userId;

	@ApiModelProperty(value = "추가되는 멤버 얼굴형", example = "255배열")
	private String face;

	@ApiModelProperty(value = "그룹장 여부", example = "true")
	private boolean isOwner;

	@ApiModelProperty(value = "그룹 멤버 이름", example = "test")
	private String name;

	@ApiModelProperty(value = "그룹 토큰", example = "1nsdf2346asGd")
	private String groupToken;

	public MemberDto() {}

	public MemberDto(int groupId, long userId, boolean isOwner) {
		this.groupId = groupId;
		this.userId = userId;
		this.isOwner = isOwner;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}

	public boolean isOwner() {
		return isOwner;
	}

	public void setOwner(boolean owner) {
		isOwner = owner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroupToken() {
		return groupToken;
	}

	public void setGroupToken(String groupToken) {
		this.groupToken = groupToken;
	}

	@Override
	public String toString() {
		return "MemberDto{" +
			"groupId=" + groupId +
			", userId=" + userId +
			", face='" + face + '\'' +
			", isOwner=" + isOwner +
			", name='" + name + '\'' +
			", groupToken='" + groupToken + '\'' +
			'}';
	}
}
