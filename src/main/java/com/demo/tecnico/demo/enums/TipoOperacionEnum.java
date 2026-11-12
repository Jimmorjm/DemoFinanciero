package com.demo.tecnico.demo.enums;

public enum TipoOperacionEnum {

	OPERACION_CARGO(1, "CARGO"), OPERACION_ABONO(2, "ABONO");

	private Integer id;
	private String nombre;

	private TipoOperacionEnum(Integer id, String nombre) {
		this.id= id;
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
