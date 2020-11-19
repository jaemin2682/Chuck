package com.ssafy.chuck.common.aspect;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.ssafy.chuck.error.exception.EntityNotFoundException;
import com.ssafy.chuck.user.dto.Response;
import com.ssafy.chuck.user.dto.UserDto;
import com.ssafy.chuck.user.service.UserService;

@Aspect
@Component
public class UserAspect {
	private static final Logger logger = LoggerFactory.getLogger(UserAspect.class);

	@Autowired
	UserService userService;

	@After("@annotation(com.ssafy.chuck.common.annotation.SignOut)")
	private void SignOut(JoinPoint point) {
		logger.debug("카카오톡 연결 끊기 시작");
		Object[] parameterValues = point.getArgs();
		String refreshToken = String.valueOf(parameterValues[2]);
		String accessToken = getAccessToken(refreshToken);
		getUnlinkId(accessToken);
	}

	@Before("@annotation(com.ssafy.chuck.common.annotation.LogOut)")
	private void Logout(JoinPoint point) {
		logger.debug("카카오톡 로그아웃");
		Object[] parameterValues = point.getArgs();
		String refreshToken = String.valueOf(parameterValues[2]);
		String accessToken = getAccessToken(refreshToken);
		parameterValues[1] = logout(accessToken);
	}

	private long logout(String accessToken) {
		String reqURL = "https://kapi.kakao.com/v1/user/logout";
		long userId = 0;
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);

			// Header에 포함될 내용
			conn.setRequestProperty("Authorization", "Bearer " + accessToken);

			String temp = this.getResponseBody(conn);

			Gson gson = new Gson();
			Response response = gson.fromJson(temp, Response.class);

			userId = Integer.parseInt(response.getId());
		} catch (IOException e) {
			e.printStackTrace();
			throw new EntityNotFoundException(String.valueOf(userId));
		}
		return userId;
	}

	private static String getResponseBody(HttpURLConnection conn) throws IOException {
		logger.debug("ResponseBody 요청 호출");
		int responseCode = conn.getResponseCode();
		System.out.println("responseCode : " + responseCode);

		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

		String line = "";
		String result = "";

		while ((line = br.readLine()) != null) {
			result += line;
		}
		// System.out.println("response body : " + result);
		br.close();
		return result;
	}

	public static Response getUserInfo(String accessToken) {
		logger.debug("유저정보 요청 호출");
		// Response Type 선언 (유저 정보 여러개 필요)
		Response response = new Response();
		String reqURL = "https://kapi.kakao.com/v2/user/me";
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");

			// Header에 포함될 내용
			conn.setRequestProperty("Authorization", "Bearer " + accessToken);

			String result = getResponseBody(conn);

			Gson gson = new Gson();
			response = gson.fromJson(result, Response.class);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	private long getUnlinkId(String accessToken) {
		logger.debug("카카오 연결 끊기 시작");
		Response response = new Response();
		String reqURL = "https://kapi.kakao.com/v1/user/unlink";
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();

			// Post 보내기 위한 설정
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);

			// Header에 포함될 내용
			conn.setRequestProperty("Authorization", "Bearer " + accessToken);
			conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");

			String result = this.getResponseBody(conn);

			Gson gson = new Gson();
			response = gson.fromJson(result, Response.class);
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
		return Long.valueOf(response.getId());
	}

	private String getAccessToken(String refreshToken) {
		logger.debug("AccessToken 호출");
		final String apiKey = "af3fbd88dda8f08e922abaa01884432b";
		String accessToken = "";

		String reqURL = "https://kauth.kakao.com/oauth/token";
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();

			// Post 보내기 위한 설정
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);

			// Header에 포함될 내용
			conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");

			// POST 요청 시 필요한 파라메터 데이터
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();

			sb.append("grant_type=refresh_token&");
			sb.append("client_id=").append(apiKey);
			sb.append("&refresh_token=").append(refreshToken);

			bw.write(sb.toString());
			bw.flush();

			String result = this.getResponseBody(conn);

			Gson gson = new Gson();
			Response response = gson.fromJson(result, Response.class);
			accessToken = response.getAccessToken();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return accessToken;
	}
}
