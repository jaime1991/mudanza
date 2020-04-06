package com.tech.and.solve.mudanza.service.domain.exception;

public class TechnicalException  extends RuntimeException {

	private static final long serialVersionUID = 8652923406242062150L;
	private final int statusCode;

    public TechnicalException(String message, int statusCode) {
        super(message);
        this.statusCode=statusCode;
    }

    public TechnicalException(String message, Throwable causa, int statusCode) {
        super(message, causa);
        this.statusCode=statusCode;
    }

    public int getStatusCode(){
        return this.statusCode;
    }

}