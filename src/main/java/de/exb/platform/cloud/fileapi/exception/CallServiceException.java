package de.exb.platform.cloud.fileapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CallServiceException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public CallServiceException(String message) {
		super(message);
	}
}
