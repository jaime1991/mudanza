package com.tech.and.solve.mudanza.service.infraestructure.persistence.mongo.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.tech.and.solve.mudanza.service.infraestructure.persistence.mongo.model.ViajesResultModel;

public interface MudanzaRepository extends ReactiveCrudRepository<ViajesResultModel, String>{

}
