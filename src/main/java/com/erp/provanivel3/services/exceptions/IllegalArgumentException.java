package com.erp.provanivel3.services.exceptions;

public class IllegalArgumentException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IllegalArgumentException(String msg) {
		super(msg);
	}

	public IllegalArgumentException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
