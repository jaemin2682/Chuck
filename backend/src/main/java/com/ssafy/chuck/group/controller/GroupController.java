package com.ssafy.chuck.group.controller;

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
import com.ssafy.chuck.group.dto.GroupDto;
import com.ssafy.chuck.group.dto.MemberDto;
import com.ssafy.chuck.group.service.GroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@Api(value = "그룹 관리", tags = "Group")
@RestController
@RequestMapping("/chuck/groups")
public class GroupController {
	private static final Logger logger = LoggerFactory.getLogger(GroupController.class);

	@Autowired
	GroupService service;

	@Autowired
	PermissionCheck permissionCheck;

	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "그룹 생성", notes = "새로운 그룹을 생성한다.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "그룹 생성 성공"),
		@ApiResponse(code = 400, message = "잘못된 요청입니다"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 403, message = "권한이 없습니다"),
		@ApiResponse(code = 404, message = "그룹 생성 실패")
	})
	private ResponseEntity<?> create(@RequestBody GroupDto group, @RequestHeader(value = "token") String token) {
		logger.debug("그룹 생성 호출");
		group.setUserId(permissionCheck.check(token).getId());
		return new ResponseEntity<>(service.create(group), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	@ApiOperation(value = "그룹 상세 조회", notes = "그룹 정보를 상세 조회한다.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "그룹 상세 조회 성공"),
		@ApiResponse(code = 400, message = "잘못된 요청입니다"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 403, message = "권한이 없습니다"),
		@ApiResponse(code = 404, message = "그룹 상세 조회 실패")
	})
	private ResponseEntity<?> read(@PathVariable(value = "id") int id, @RequestHeader(value = "token") String token) {
		logger.debug("그룹 상세 조회 호출");
		long userId = permissionCheck.check(token).getId();
		GroupDto group = service.read(userId, 0, id);
		return new ResponseEntity<>(group, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	@ApiOperation(value = "그룹 수정", notes = "그룹을 수정한다.")
	@ApiResponses({
		@ApiResponse(code = 201, message = "그룹 수정 성공"),
		@ApiResponse(code = 400, message = "잘못된 요청입니다"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 403, message = "권한이 없습니다"),
		@ApiResponse(code = 404, message = "그룹 수정 실패")
	})
	private ResponseEntity<?> update(@RequestBody GroupDto group, @RequestHeader(value = "token") String token) {
		logger.debug("그룹 수정 호출");
		service.update(group, permissionCheck.check(token).getId());
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	@ApiOperation(value = "그룹 삭제", notes = "그룹을 삭제한다.")
	@ApiResponses({
		@ApiResponse(code = 204, message = "그룹 삭제 성공"),
		@ApiResponse(code = 400, message = "잘못된 요청입니다"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 403, message = "권한이 없습니다"),
		@ApiResponse(code = 404, message = "그룹 삭제 실패")
	})
	private ResponseEntity<?> delete(@PathVariable(value = "id") int id, @RequestHeader(value = "token") String token) {
		logger.debug("그룹 삭제 호출");
		long userId = permissionCheck.check(token).getId();
		service.delete(new GroupDto(id), userId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}/{userId}")
	@ApiOperation(value = "그룹내 멤버 삭제", notes = "그룹내 멤버를 삭제한다.")
	@ApiResponses({
		@ApiResponse(code = 204, message = "그룹내 멤버 삭제 성공"),
		@ApiResponse(code = 400, message = "잘못된 요청입니다"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 403, message = "권한이 없습니다"),
		@ApiResponse(code = 404, message = "그룹내 멤버 삭제 실패")
	})
	private ResponseEntity<?> delete(@PathVariable(value = "id") int id, @PathVariable(value = "userId") int userId,
		@RequestHeader(value = "token") String token) {
		logger.debug("그룹내 멤버 삭제 호출");
		long requestId = permissionCheck.check(token).getId();
		service.deleteMember(id, userId, requestId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/of/groups")
	@ApiOperation(value = "유저 그룹 리스트 조회", notes = "유저의 모든 그룹 리스트를 조회한다.")
	@ApiResponses({
		@ApiResponse(code = 201, message = "유저 그룹 리스트 조회 성공"),
		@ApiResponse(code = 400, message = "잘못된 요청입니다"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 403, message = "권한이 없습니다"),
		@ApiResponse(code = 404, message = "유저 그룹 리스트 조회 실패")
	})
	private ResponseEntity<?> readAllMember(@RequestHeader(value = "token") String token) {
		logger.debug("그룹 멤버 리스트 조회 호출");
		long userId = permissionCheck.check(token).getId();
		List<GroupDto> list = service.readAllGroup(userId);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/members")
	@ApiOperation(value = "그룹 멤버 추가", notes = "그룹에 멤버를 추가한다.")
	@ApiResponses({
		@ApiResponse(code = 201, message = "그룹 멤버 추가 성공"),
		@ApiResponse(code = 400, message = "잘못된 요청입니다"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 403, message = "권한이 없습니다"),
		@ApiResponse(code = 404, message = "그룹 멤버 추가 실패")
	})
	private ResponseEntity<?> createMember(@RequestBody MemberDto member) {
		logger.debug("그룹 멤버 추가 호출");
		service.createMember(member);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}/members")
	@ApiOperation(value = "그룹 멤버 리스트 조회", notes = "그룹멤버 리스트를 조회한다.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "그룹 멤버 리스트 조회 성공"),
		@ApiResponse(code = 400, message = "잘못된 요청입니다"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 403, message = "권한이 없습니다"),
		@ApiResponse(code = 404, message = "그룹 멤버 리스트 조회 실패")
	})
	private ResponseEntity<?> readAllMember(@PathVariable(value = "id") int id, @RequestHeader(value = "token") String token) {
		logger.debug("그룹 멤버 리스트 조회 호출");
		// long userId = permissionCheck.check(token).getId();
		List<MemberDto> list = service.readAllMember(0, 0, id);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/changes/{id}")
	@ApiOperation(value = "그룹장 변경을 요청", notes = "그룹장 변경을 요청한다.")
	@ApiResponses({
		@ApiResponse(code = 201, message = "그룹장 변경을 요청 성공"),
		@ApiResponse(code = 400, message = "잘못된 요청입니다"),
		@ApiResponse(code = 401, message = "로그인 후 이용해 주세요"),
		@ApiResponse(code = 403, message = "권한이 없습니다"),
		@ApiResponse(code = 404, message = "그룹장 변경을 요청 실패")
	})
	private ResponseEntity<?> changeOwner(@PathVariable(value = "id") int id, @RequestHeader(value = "token")
		String token, @RequestBody GroupDto group) {
		logger.debug("그룹장 변경을 요청 호출");
		long userId = permissionCheck.check(token).getId();
		service.change(userId, 0, group.getId(), id, group);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}
