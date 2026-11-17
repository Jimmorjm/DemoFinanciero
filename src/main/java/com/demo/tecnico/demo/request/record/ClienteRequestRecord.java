package com.demo.tecnico.demo.request.record;

import java.util.Date;

public record ClienteRequestRecord(String nombreCompleto, String direccion, String numeroCliente, Date fechaCreacion,
		Date fechaActualizacion) {

}
