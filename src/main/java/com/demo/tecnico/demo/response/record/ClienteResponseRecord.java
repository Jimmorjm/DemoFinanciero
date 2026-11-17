package com.demo.tecnico.demo.response.record;

import java.util.Date;

public record ClienteResponseRecord(Integer idCliente, String nombreCompleto, String direccion, String numeroCliente,
		Date fechaCreacion, Date fechaActualizacion) {

}
