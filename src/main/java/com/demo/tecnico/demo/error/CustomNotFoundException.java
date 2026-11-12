package com.demo.tecnico.demo.error;

public class CustomNotFoundException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public CustomNotFoundException(String mensaje) {
		super(mensaje);
	}

}
