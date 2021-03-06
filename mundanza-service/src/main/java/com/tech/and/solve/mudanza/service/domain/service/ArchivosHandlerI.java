package com.tech.and.solve.mudanza.service.domain.service;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

public interface ArchivosHandlerI {

	Mono<ServerResponse> uploadFile(ServerRequest request);
	
}
