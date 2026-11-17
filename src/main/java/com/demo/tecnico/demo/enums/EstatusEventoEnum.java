package com.demo.tecnico.demo.enums;

public enum EstatusEventoEnum {
	APROVADA("APROVADA"),
	RECHAZADA("RECHAZADA"),
	DUPLICADA("DUPLICADA");

	private String nombre;

	EstatusEventoEnum(String nombre) {
		this.nombre= nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
