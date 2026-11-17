package com.demo.tecnico.demo.enums;

public enum EstatusCuentaEnum {

	ESTATUS_ACTIVO("ACTIVA"), ESTATUS_BLOQUEADA("BLOQUEADA");

	private String nombre;

	private EstatusCuentaEnum(String nombre) {
		this.nombre= nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
