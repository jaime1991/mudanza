package com.tech.and.solve.mudanza.service.infraestructure.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.tech.and.solve.mudanza.service.domain.exception.BusinessException;
import com.tech.and.solve.mudanza.service.domain.exception.TechnicalException;
import com.tech.and.solve.mudanza.service.domain.service.ArchivosHandlerI;
import com.tech.and.solve.mudanza.service.infraestructure.dto.EstadoCargaDto;

import reactor.core.publisher.Mono;

@Service
public class ArchivosHandlerImpl implements ArchivosHandlerI {

	private Logger log = LoggerFactory.getLogger(ArchivosHandlerImpl.class);
	
	@Override
	public Mono<ServerResponse> uploadFile(ServerRequest request) {
		return request.body(BodyExtractors.toMultipartData()).flatMap(p -> {
			Map<String, Part> parts = p.toSingleValueMap();
			FilePart fp = (FilePart) parts.get("file");
			File tmp = new File(UUID.randomUUID().toString().concat(fp.filename()));
			fp.transferTo(tmp);
			return ServerResponse.ok().body(validarEstadoCarga(tmp.getName()), EstadoCargaDto.class);
		}).onErrorResume(throwable -> Mono.error(new Exception(throwable)));
	}

	private Mono<List<Integer>> obtenerDatos(String file) {
		return Mono.just(file).map(pathFile -> new File(pathFile)).flatMap(archivo -> {
			List<Integer> datos = null;
			try (InputStream inputFS = new FileInputStream(archivo)) {
				BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));
				datos = br.lines().map(Integer::parseInt).filter(peso -> peso > 0).collect(Collectors.toList());
				br.close();
			} catch (FileNotFoundException e) {
				log.error("Archivo no encontrado", e);
				return Mono.error(new TechnicalException(e.getMessage(), 500));
			} catch (IOException e) {
				log.error("Error de entrada/salida de datos", e);
				return Mono.error(new TechnicalException(e.getMessage(), 500));
			} catch (NumberFormatException e) {
				log.error("Error al procesar el archivo", e);
				return Mono.error(new BusinessException(e.getMessage(), 500));
			}
			return Mono.just(datos);
		});
	}

	private Mono<EstadoCargaDto> validarEstadoCarga(String file) {
		return obtenerDatos(file)
				.map(list -> contendioValido(list) ? new EstadoCargaDto("200", "recurso correcto", file)
						: new EstadoCargaDto("500", "el recurso no es valido", null));
	}

	private boolean contendioValido(List<Integer> datos) {
		if (datos == null || datos.isEmpty()) {
			return false;
		}

		int diasDeTrabajo = datos.get(0);
		if (diasDeTrabajo == 0) {
			return false;
		}

		int cantidadElementos = 1;
		for (int i = 0; i < diasDeTrabajo; i++) {
			cantidadElementos = datos.get(cantidadElementos) + cantidadElementos + 1;
		}

		return cantidadElementos == datos.size();
	}

}
