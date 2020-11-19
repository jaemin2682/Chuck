package com.ssafy.chuck.reply.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.chuck.reply.dto.ReplyDto;
import com.ssafy.chuck.reply.service.ReplyService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

// http://localhost:8888/swagger-ui/
@CrossOrigin(origins = {"*"}, maxAge = 6000)
@Api(value = "댓글 관리", tags = "Reply")
@RestController
@RequestMapping("/chuck/replies")
public class ReplyController {
	static final Logger logger = LoggerFactory.getLogger(ReplyController.class);

	@Autowired
	private ReplyService replyService;

	@PostMapping("/insert")
	@ApiOperation(value = "댓글 입력")
	public ResponseEntity<?> insertComment(@RequestBody ReplyDto reply) {
		replyService.insertComment(reply, 2);
		return new ResponseEntity<>(reply , HttpStatus.OK);
	}

	@GetMapping("/searchByWriter")
	@ApiOperation(value = "작성자 ID로 댓글 조회")
	public ResponseEntity<List<ReplyDto>> selectCommentByWriter(long writer) {	
		return new ResponseEntity<List<ReplyDto>>(replyService.selectCommentByWriter(writer), HttpStatus.OK);
	}

	@GetMapping("/searchByDiary")
	@ApiOperation(value = "다이어리 ID로 댓글 조회")
	public ResponseEntity<?> selectCommentByDiaryId(int diaryId) {
		return new ResponseEntity<>(replyService.selectCommentByDiaryId(diaryId), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "댓글 ID로 댓글 삭제")
	public ResponseEntity<?> delete(@PathVariable(value = "id") int id) {
		replyService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
