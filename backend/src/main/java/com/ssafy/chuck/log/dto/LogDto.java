package com.ssafy.chuck.log.dto;

import io.swagger.annotations.ApiModelProperty;

public class LogDto {

	@ApiModelProperty(value = "로그 아이디", example = "1")
	private int id;

	@ApiModelProperty(value = "그룹 아이디", example = "1")
	private int groupId;

	@ApiModelProperty(value = "로그 내용", example = "OOO님이 OO그룹에 가입하셨습니다.")
	private String content;

	@ApiModelProperty(value = "해당 이벤트 유저 아이디", example = "12468731")
	private long userId;

	@ApiModelProperty(value = "해당 이벤트 다이어리 또는 댓글 아이디", example = "1")
	private int writeId;

	public LogDto() {}

	public LogDto(int groupId, String content) {
		this.groupId = groupId;
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getWriteId() {
		return writeId;
	}

	public void setWriteId(int writeId) {
		this.writeId = writeId;
	}


	@Override
	public String toString() {
		return "LogDto{" +
			"id=" + id +
			", groupId=" + groupId +
			", content='" + content + '\'' +
			", userId=" + userId +
			", writeId=" + writeId +
			'}';
	}
}
