package com.tech.and.solve.mudanza.service.application;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.tech.and.solve.mudanza.service.domain.service.ArchivosHandlerI;
import com.tech.and.solve.mudanza.service.domain.service.ViajesHandlerI;

@Component
public class RouterFunctionConfig {

	@Autowired
	ArchivosHandlerI archivosHandler;
	@Autowired
	ViajesHandlerI viajesHandler;
	
	@Bean
	public RouterFunction<ServerResponse> verificarCantidadViajes(){
		return route(POST("/upload-file"), archivosHandler::uploadFile)
				.andRoute(GET("/verificar-viajes/{resource}/{documento}"), viajesHandler::verificarViajes);
	}
	
}
