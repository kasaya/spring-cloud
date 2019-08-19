package com.oycl.exception;
public class BaseException extends  Exception{
    /**
	 * 
	 */
	private static final long serialVersionUID = -3147797027971132314L;

	public BaseException(String message) {
        super(message);
    }

	public BaseException(Throwable e) {
		super(e);
	}
}
