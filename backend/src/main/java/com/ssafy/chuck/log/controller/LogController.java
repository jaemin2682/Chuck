package com.ssafy.chuck.log.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.chuck.log.dto.LogDto;
import com.ssafy.chuck.log.service.LogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@Api(value = "활동로그 관리", tags = "Log")
@RestController
@RequestMapping("/chuck/logs")
public class LogController {
	static final Logger logger = LoggerFactory.getLogger(LogController.class);

	@Autowired
	LogService service;

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	@ApiOperation(value = "로그 조회", notes = "그룹 로그를 조회한다.")
	@ApiResponses({
		@ApiResponse(code = 201, message = "로그 조회 성공"),
		@ApiResponse(code = 400, message = "잘못된 요청입니다"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 403, message = "권한이 없습니다"),
		@ApiResponse(code = 404, message = "로그 조회 실패")
	})
	private ResponseEntity<?> readAll(@PathVariable(value = "id")  int id) {
		logger.debug("로그 조회 호출");
		List<LogDto> result = service.readAll(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
