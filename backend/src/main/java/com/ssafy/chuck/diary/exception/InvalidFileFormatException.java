package com.ssafy.chuck.diary.exception;

import com.ssafy.chuck.error.exception.IncorrectFormatException;

public class InvalidFileFormatException extends IncorrectFormatException {
	public InvalidFileFormatException() {
		super("Image file's format is not accepted.");
	}
}
