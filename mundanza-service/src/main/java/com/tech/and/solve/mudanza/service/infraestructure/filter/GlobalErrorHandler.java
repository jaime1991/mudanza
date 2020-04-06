package com.tech.and.solve.mudanza.service.infraestructure.filter;

import reactor.core.publisher.Mono;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.and.solve.mudanza.service.domain.exception.BusinessException;
import com.tech.and.solve.mudanza.service.infraestructure.dto.HttpExceptionDto;

import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.server.ServerWebExchange;

@Configuration
@Order(-2)
public class GlobalErrorHandler implements ErrorWebExceptionHandler {

	private ObjectMapper objectMapper;

	public GlobalErrorHandler(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public Mono<Void> handle(ServerWebExchange serverWebExchange, Throwable throwable) {
		DataBufferFactory bufferFactory = serverWebExchange.getResponse().bufferFactory();
		serverWebExchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
		if (throwable instanceof BusinessException) {
			serverWebExchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
		}

		throwable.printStackTrace();
		
		DataBuffer dataBuffer = null;
		try {
			dataBuffer = bufferFactory.wrap(objectMapper
					.writeValueAsBytes(new HttpExceptionDto(throwable.getClass().getName(), throwable.getMessage())));
		} catch (JsonProcessingException e) {
			dataBuffer = bufferFactory.wrap("".getBytes());
		}
		serverWebExchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
		return serverWebExchange.getResponse().writeWith(Mono.just(dataBuffer));

	}
}