package com.ssafy.chuck.user.dto;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

public class Response {

	@SerializedName("access_token")
	String accessToken;

	@SerializedName("refresh_token")
	String refreshToken;

	@SerializedName("id")
	String id;

	@SerializedName("properties")
	Map<String, String> data = new HashMap<>();

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}
}
