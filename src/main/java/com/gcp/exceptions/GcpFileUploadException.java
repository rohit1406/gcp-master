package com.gcp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class GcpFileUploadException extends RuntimeException {
	public GcpFileUploadException() {
		super();
	}

	public GcpFileUploadException(String message) {
		super(message);
	}
}
