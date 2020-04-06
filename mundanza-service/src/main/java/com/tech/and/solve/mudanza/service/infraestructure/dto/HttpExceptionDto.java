package com.tech.and.solve.mudanza.service.infraestructure.dto;

public class HttpExceptionDto {

	private String type;
	private String message;
	
	public HttpExceptionDto(String type, String message) {
		super();
		this.type = type;
		this.message = message;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
