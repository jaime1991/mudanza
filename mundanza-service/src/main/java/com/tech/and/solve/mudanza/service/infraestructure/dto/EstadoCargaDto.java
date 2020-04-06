package com.tech.and.solve.mudanza.service.infraestructure.dto;

public class EstadoCargaDto {

	private String codigo;
	private String descripcion;
	private String recursoId;
		
	public EstadoCargaDto(String codigo, String descripcion, String recursoId) {
		super();
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.recursoId = recursoId;
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
	public String getRecursoId() {
		return recursoId;
	}
	public void setRecursoId(String recursoId) {
		this.recursoId = recursoId;
	}
	
}
