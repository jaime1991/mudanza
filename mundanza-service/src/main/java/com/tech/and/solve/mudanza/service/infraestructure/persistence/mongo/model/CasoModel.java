package com.tech.and.solve.mudanza.service.infraestructure.persistence.mongo.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class CasoModel {

	private List<Integer> elementos;
	private int cantidadViajes;

	public CasoModel() {}
	
	public CasoModel(List<Integer> elementos, int cantidadViajes) {
		super();
		this.elementos= elementos;
		this.cantidadViajes = cantidadViajes;
	}

	public List<Integer> getElementos() {
		return elementos;
	}

	public void setElementos(List<Integer> elementos) {
		this.elementos = elementos;
	}

	public int getCantidadViajes() {
		return cantidadViajes;
	}

	public void setCantidadViajes(int cantidadViajes) {
		this.cantidadViajes = cantidadViajes;
	}

}
