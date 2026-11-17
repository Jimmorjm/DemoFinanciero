package com.demo.tecnico.demo.enums;

public enum OperacionEstatusEnum {
	PENDIENTE("PENDIENTE"), APLICADA("APLICADA");

	private String nombre;

	private OperacionEstatusEnum(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
