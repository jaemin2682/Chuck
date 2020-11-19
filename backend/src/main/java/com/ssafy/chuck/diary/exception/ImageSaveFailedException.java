package com.ssafy.chuck.diary.exception;

import com.ssafy.chuck.error.exception.BusinessException;
import com.ssafy.chuck.error.exception.ErrorCode;

public class ImageSaveFailedException extends BusinessException {
	public ImageSaveFailedException() {
		super("Image save failed. Please check file route.", ErrorCode.FILE_SAVE_FAILED);
	}
}
