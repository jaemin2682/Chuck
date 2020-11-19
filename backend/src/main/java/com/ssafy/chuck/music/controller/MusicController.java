package com.ssafy.chuck.music.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.chuck.music.service.MusicService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@Api(value = "음악 관리", tags = "Music")
@RestController
@RequestMapping("/chuck/music")
public class MusicController {
	static final Logger logger = LoggerFactory.getLogger(MusicController.class);

	@Autowired
	MusicService service;

	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "음악 리스트 조회", notes = "음악을 조회한다.")
	@ApiResponses({
		@ApiResponse(code = 201, message = "음악 리스트 조회 성공"),
		@ApiResponse(code = 400, message = "잘못된 요청입니다"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 403, message = "권한이 없습니다"),
		@ApiResponse(code = 404, message = "음악 리스트 조회 실패")
	})
	private ResponseEntity<?> readAll() {
		logger.debug("음악 리스트 조회 호출");
		return new ResponseEntity<>(service.readAll(), HttpStatus.CREATED);
	}
}
