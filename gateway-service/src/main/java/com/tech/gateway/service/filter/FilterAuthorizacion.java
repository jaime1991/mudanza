package com.tech.gateway.service.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.gateway.service.dto.HttpExceptionDto;

import reactor.core.publisher.Mono;

@Component
public class FilterAuthorizacion extends AbstractGatewayFilterFactory<FilterAuthorizacion.Config> {

	@Autowired
	private ObjectMapper objectMapper;

	public FilterAuthorizacion() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();

			if (!request.getPath().value().contains("verificar-viajes")) {

				if (!request.getHeaders().containsKey("Authorization")) {
					return this.onError(exchange, "No Authorization header", HttpStatus.UNAUTHORIZED);
				}

				String authorizationHeader = request.getHeaders().get("Authorization").get(0);

				if (!this.isAuthorizationValid(authorizationHeader)) {
					return this.onError(exchange, "Invalid Authorization header", HttpStatus.UNAUTHORIZED);
				}
			}

			return chain.filter(exchange);
		};
	}

	private boolean isAuthorizationValid(String authorizationHeader) {
		return !authorizationHeader.isEmpty();
	}

	private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
		DataBufferFactory bufferFactory = exchange.getResponse().bufferFactory();
		DataBuffer dataBuffer = null;
		try {
			dataBuffer = bufferFactory
					.wrap(objectMapper.writeValueAsBytes(new HttpExceptionDto(httpStatus.toString(), err)));
		} catch (JsonProcessingException e) {
			dataBuffer = bufferFactory.wrap("".getBytes());
		}

		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(httpStatus);
		response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

		return response.writeWith(Mono.just(dataBuffer));
	}

	public static class Config {
		// propiedades de configuracion
	}
}