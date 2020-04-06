package com.tech.and.solve.mudanza.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Flux;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class AceptacionTest {

	@Autowired
	private WebTestClient webClient;
	
	@Test
	@DisplayName("Deberia responder con un estado exitoso")
	void cargaDeArchivoConExito() {
		MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();

		Flux<DataBuffer> data = DataBufferUtils.read(
		            Paths.get("src/test/java/com/tech/and/solve/mudanza/service/lazy_loading_example_input.txt"), new DefaultDataBufferFactory(), 8);

		bodyBuilder.asyncPart("file", data, DataBuffer.class)
		            .filename("lazy_loading_example_input.txt");
		
		webClient.post().uri("/upload-file")
		.contentType(MediaType.MULTIPART_FORM_DATA)
		.bodyValue(bodyBuilder.build())
        .exchange()
        .expectStatus().isOk()
        .expectHeader().contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$.codigo").isNotEmpty()
        .jsonPath("$.codigo").isEqualTo("200");
	}
	
	@Test
	@DisplayName("Deberia responder con un error tecnico")
	void cargaDeArchivoConErrorTecnico() {
		webClient.post().uri("/upload-file")
		.contentType(MediaType.MULTIPART_FORM_DATA)
		.bodyValue("")
        .exchange()
        .expectStatus().is5xxServerError()
        .expectBody()
        .jsonPath("$.type").isNotEmpty();
	}
	
	@Test
	@DisplayName("Deberia responder con un error de negocio")
	void cargaDeArchivoConErrorDeNegocio() {
		MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();

		Flux<DataBuffer> data = DataBufferUtils.read(
		            Paths.get("src/test/java/com/tech/and/solve/mudanza/service/lazy_loading_example_input_negocio.txt"), new DefaultDataBufferFactory(), 8);

		bodyBuilder.asyncPart("file", data, DataBuffer.class)
		            .filename("lazy_loading_example_input_negocio.txt");
		
		webClient.post().uri("/upload-file")
		.contentType(MediaType.MULTIPART_FORM_DATA)
		.bodyValue(bodyBuilder.build())
        .exchange()
        .expectStatus().isBadRequest()
        .expectHeader().contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .jsonPath("$.type").isNotEmpty();
	}
	
	@Test
	@DisplayName("Deberia responder con exito al verificar los casos")
	void verificarViajesConExito() {
		List<String> dataEvent = webClient.get().uri("/verificar-viajes/309b1672-6f9e-43dc-881b-e10f2658883blazy_loading_example_input.txt/1234567")
		.accept(MediaType.valueOf(MediaType.TEXT_EVENT_STREAM_VALUE))
        .exchange()
        .expectStatus().isOk()
        .returnResult(String.class)
        .getResponseBody()
        .take(5) // take 3 comment objects
        .collectList()
        .block();
		
		assertEquals(5, dataEvent.size());
	}
	
	

}
