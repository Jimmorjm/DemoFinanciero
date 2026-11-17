package com.demo.tecnico.demo.enums;

public enum TipoOperacionEnum {

	OPERACION_CARGO("CARGO"), OPERACION_ABONO("ABONO");

	private String nombre;

	private TipoOperacionEnum(String nombre) {
		this.nombre= nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
