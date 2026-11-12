package com.demo.tecnico.demo.error;

public class CustomBadRequestException extends RuntimeException{


	private static final long serialVersionUID = 1L;

	public CustomBadRequestException(String mensaje) {
		super(mensaje);
	}

}
