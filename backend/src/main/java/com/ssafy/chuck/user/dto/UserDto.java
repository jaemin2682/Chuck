package com.ssafy.chuck.user.dto;

import java.util.Date;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;

public class UserDto {

	@SerializedName("ID")
	@ApiModelProperty(value = "회원 아이디", example = "1412733569")
	private long id;

	@ApiModelProperty(value = "최근 방문일", example = "2020-07-23")
	private Date lastDate;

	@SerializedName("NAME")
	@ApiModelProperty(value = "회원 닉네임", example = "바나나먹는몽키")
	private String name;

	public UserDto() {}

	public UserDto(long userId, String name) {
		this.id = userId;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getLastDate() {
		return lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[User_ID : ").append(this.id).append(", User_Name : ").append(this.name).append(", Last_Date : "
		).append(this.lastDate).append(", IsManager : ").append(" ]");
		return sb.toString();
	}
}
