package com.demo.tecnico.demo.enums;

public enum OperacionEstatusEnum {
	PENDIENTE(1, "PENDIENTE"), APLICADA(2, "APLICADA"), RECHAZADA(3, "RECHAZADA"), DUPLICADA(4, "DUPLICADA");

	private Integer id;
	private String nombre;

	private OperacionEstatusEnum(Integer id, String nombre) {
		this.id = id;
		this.nombre = nombre;
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
