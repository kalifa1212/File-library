package com.hforge.exceptions;

import lombok.Getter;

public class FileNotFoundException extends RuntimeException{
	
	@Getter
	private ErrorCodes errorCodes;
	
	public FileNotFoundException(String message) {
		super(message);
	}
	public FileNotFoundException(String message,Throwable cause) {
		super(message,cause);
	}
	public FileNotFoundException(String message,Throwable cause, ErrorCodes errorCodes) {
		super(message,cause);
		this.errorCodes=errorCodes;
	}
	public FileNotFoundException(String message, ErrorCodes errorCodes) {
		super(message);
		this.errorCodes=errorCodes;

	}
}
