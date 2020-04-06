package com.tech.and.solve.mudanza.service.infraestructure.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.tech.and.solve.mudanza.service.domain.exception.BusinessException;
import com.tech.and.solve.mudanza.service.domain.exception.TechnicalException;
import com.tech.and.solve.mudanza.service.domain.model.Caso;
import com.tech.and.solve.mudanza.service.domain.service.ViajesHandlerI;
import com.tech.and.solve.mudanza.service.infraestructure.persistence.mongo.model.CasoModel;
import com.tech.and.solve.mudanza.service.infraestructure.persistence.mongo.model.ViajesResultModel;
import com.tech.and.solve.mudanza.service.infraestructure.persistence.mongo.repository.MudanzaRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ViajesHandlerImpl implements ViajesHandlerI {

	@Autowired
	MudanzaRepository repository;

	@Override
	public Mono<ServerResponse> verificarViajes(ServerRequest request) {
		ViajesResultModel model = new ViajesResultModel();
		model.setDocumento(request.pathVariable("documento"));
		model.setNombreArchivo(request.pathVariable("resource"));
		return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM)
				.body(obtenerDatos(request.pathVariable("resource")).map(listaDatos -> obtenerCasos(listaDatos))
						.flatMapMany(Flux::fromIterable)
						.delayElements(Duration.ofSeconds(1))
						.doOnNext(caso -> {
							model.getCasos().add(new CasoModel(caso.getElementosPorTransportar(), caso.getCantidadViajes()));
						})
						.doOnComplete(() -> repository.save(model).subscribe())
						, Caso.class)
				.onErrorResume(throwable -> Mono.error(new Exception(throwable)));
	}

	private Mono<List<Integer>> obtenerDatos(String file) {
		return Mono.just(file).map(pathFile -> new File(pathFile)).flatMap(archivo -> {
			List<Integer> datos = null;
			try (InputStream inputFS = new FileInputStream(archivo)) {
				BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));
				datos = br.lines().map(Integer::parseInt).filter(peso -> peso > 0).collect(Collectors.toList());
				br.close();
			} catch (FileNotFoundException e) {
				return Mono.error(new TechnicalException(e.getMessage(), 500));
			} catch (IOException e) {
				return Mono.error(new TechnicalException(e.getMessage(), 500));
			} catch (NumberFormatException e) {
				return Mono.error(new BusinessException(e.getMessage(), 500));
			}
			return Mono.just(datos);
		});
	}

	private List<Caso> obtenerCasos(List<Integer> datos) {
		List<Caso> casos = new ArrayList<>(datos.get(0));

		int indice = 1;
		for (int i = 0; i < datos.get(0); i++) {
			int cantidadElementos = datos.get(indice);
			List<Integer> elementos = new ArrayList<>(cantidadElementos);
			for (int j = 0; j < cantidadElementos; j++) {
				indice++;
				elementos.add(datos.get(indice));
			}
			casos.add(new Caso(elementos, datos.get(0)));
			indice++;
		}
		return casos;
	}

}
