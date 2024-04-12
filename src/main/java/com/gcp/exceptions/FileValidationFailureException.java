package com.gcp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class FileValidationFailureException extends RuntimeException {
	public FileValidationFailureException() {
		super();
	}

	public FileValidationFailureException(String message) {
		super(message);
	}
}
