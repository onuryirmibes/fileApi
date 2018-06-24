package de.exb.platform.cloud.fileapi.exception;

public class NotSpecifiedRequest extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NotSpecifiedRequest(String message) {
		super(message);
	}
	
	public NotSpecifiedRequest(String message, Exception ex) {
		super(message, ex);
	}
}
