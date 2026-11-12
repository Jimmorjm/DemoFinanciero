package com.demo.tecnico.demo.enums;

public enum EstatusCuentaEnum {

	ESTATUS_ACTIVO(1, "ACTIVA"), ESTATUS_BLOQUEADA(2, "BLOQUEADA");

	private Integer id;
	private String nombre;

	private EstatusCuentaEnum(Integer id, String nombre) {
		this.id=id;
		this.nombre= nombre;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
