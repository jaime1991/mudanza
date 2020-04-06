package com.tech.and.solve.mudanza.service.infraestructure.dto;

public class EstadoCargaDto {

	private String codigo;
	private String descripcion;
	private String recurso_id;
		
	public EstadoCargaDto(String codigo, String descripcion, String recurso_id) {
		super();
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.recurso_id = recurso_id;
	}
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getRecurso_id() {
		return recurso_id;
	}
	public void setRecurso_id(String recurso_id) {
		this.recurso_id = recurso_id;
	}
	
}
