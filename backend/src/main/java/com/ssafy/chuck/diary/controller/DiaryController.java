package com.ssafy.chuck.diary.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.chuck.common.PermissionCheck;
import com.ssafy.chuck.diary.dto.DiaryDto;
import com.ssafy.chuck.diary.service.DiaryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@Api(value = "다이어리 관리", tags = "Diary")
@RestController
@RequestMapping("/chuck/diaries")
public class DiaryController {
	static final Logger logger = LoggerFactory.getLogger(DiaryController.class);

	@Autowired
	DiaryService service;

	@Autowired
	PermissionCheck permissionCheck;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ApiOperation(value = "다이어리 생성", notes = "그룹의 추억 쌓기", response = DiaryDto.class)
	@ApiResponses({
		@ApiResponse(code = 200, message = "다이어리 생성 성공"),
		@ApiResponse(code = 400, message = "잘못된 요청입니다"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 403, message = "권한이 없습니다"),
		@ApiResponse(code = 404, message = "다이어리 생성 실패")
	})
	private ResponseEntity<?> create(@RequestBody DiaryDto diary, @RequestHeader(value = "token") String token) {
		logger.debug("다이어리 생성 호출");
		System.out.println(diary.toString());
		long userId = permissionCheck.check(token).getId();
		service.create(userId, 1, diary);
		return new ResponseEntity<>(diary, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}", produces = "application/json")
	@ApiOperation(value = "다이어리 상세 조회", notes = "그룹의 추억 읽기", response = DiaryDto.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "회원 토큰"),
	})
	@ApiResponses({
		@ApiResponse(code = 200, message = "다이어리 상세 조회 성공"),
		@ApiResponse(code = 400, message = "잘못된 요청입니다"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 403, message = "권한이 없습니다"),
		@ApiResponse(code = 404, message = "다이어리 상세 조회 실패")
	})
	private ResponseEntity<?> read(@PathVariable int id, @RequestHeader(value = "token") String token) {
		logger.debug("다이어리 상세 조회 호출");
		// long userId = permissionCheck.check(token).getId();
		DiaryDto diary = service.read(id);
		return new ResponseEntity<>(diary, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}", produces = "application/json")
	@ApiOperation(value = "다이어리 수정", notes = "그룹의 추억 수정", response = DiaryDto.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "회원 토큰"),
	})
	@ApiResponses({
		@ApiResponse(code = 201, message = "다이어리 수정 성공"),
		@ApiResponse(code = 400, message = "잘못된 요청입니다"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 403, message = "권한이 없습니다"),
		@ApiResponse(code = 404, message = "다이어리 수정 실패")
	})
	private ResponseEntity<?> update(@RequestBody DiaryDto diary, @RequestHeader(value = "token") String token) {
		logger.debug("다이어리 수정 호출");
		long userId = permissionCheck.check(token).getId();
		service.update(userId, diary);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}", produces = "application/json")
	@ApiOperation(value = "다이어리 삭제", notes = "다이어리 삭제")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "token", value = "회원 토큰"),
	})
	@ApiResponses({
		@ApiResponse(code = 204, message = "다이어리 삭제 성공"),
		@ApiResponse(code = 400, message = "잘못된 요청입니다"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 403, message = "권한이 없습니다"),
		@ApiResponse(code = 404, message = "다이어리 삭제 실패")
	})
	private ResponseEntity<?> delete(@PathVariable("id") int id, @RequestHeader(value = "token") String token) {
		logger.debug("다이어리 삭제 호출");
		long userId = permissionCheck.check(token).getId();
		service.delete(userId, id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/group/{id}", produces = "application/json")
	@ApiOperation(value = "다이어리 리스트 조회", notes = "그룹의 추억들 읽기", response = DiaryDto.class)
	@ApiResponses({
		@ApiResponse(code = 200, message = "다이어리 리스트 조회 성공"),
		@ApiResponse(code = 400, message = "잘못된 요청입니다"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 403, message = "권한이 없습니다"),
		@ApiResponse(code = 404, message = "다이어리 리스트 조회 실패")
	})
	private ResponseEntity<?> readAll(@PathVariable(value = "id") int id,
		@RequestHeader(value = "token") String token) {
		logger.debug("다이어리 리스트 조회 호출");
		// long userId = permissionCheck.check(token).getId();
		List<DiaryDto> list = service.readAll(id);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/search/{word}/{id}", produces = "application/json")
	@ApiOperation(value = "다이어리 검색", notes = "그룹의 추억 검색", response = DiaryDto.class)
	@ApiResponses({
		@ApiResponse(code = 200, message = "다이어리 검색 성공"),
		@ApiResponse(code = 400, message = "잘못된 요청입니다"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 403, message = "권한이 없습니다"),
		@ApiResponse(code = 404, message = "다이어리 검색 실패")
	})
	private ResponseEntity<?> search(@PathVariable(value = "word") String word, @PathVariable(value = "id") int id) {
		logger.debug("다이어리 검색 호출");
		List<DiaryDto> list = service.search(id, word);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
}
