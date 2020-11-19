package com.ssafy.chuck.reply.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ReplyDto {
	private int id;
	private Long writerId;
	private String writer;
	private String comment;
	private int diaryId;
	private Date date;
}
