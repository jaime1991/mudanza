package com.tech.and.solve.mudanza.service.infraestructure.persistence.mongo.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ViajesResultModel {

	@Id
	private String id;
	private String documento;
	private String nombreArchivo;
	private List<CasoModel> casos;
	private String fecha;
	
	public ViajesResultModel() {
		casos = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		fecha = sdf.format(new Date());
	}
	
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public String getNombreArchivo() {
		return nombreArchivo;
	}
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	public List<CasoModel> getCasos() {
		return casos;
	}
	public void setCasos(List<CasoModel> casos) {
		this.casos = casos;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
}