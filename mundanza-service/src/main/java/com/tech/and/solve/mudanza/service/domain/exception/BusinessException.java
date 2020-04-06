package com.tech.and.solve.mudanza.service.domain.exception;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -7146765975742351478L;
	private int statusCode;

    public BusinessException(String message, int statusCode) {
        super(message);
        this.statusCode=statusCode;
    }

    public BusinessException(String message, Throwable causa, int statusCode) {
        super(message, causa);
        this.statusCode=statusCode;
    }

    public int getStatusCode(){
        return this.statusCode;
    }

}
