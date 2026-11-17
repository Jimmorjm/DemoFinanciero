package com.demo.tecnico.demo.response.record;

import java.math.BigDecimal;

public record CuentaResponseRecord(Integer idCuenta, Integer idCliente, String numeroCuenta, BigDecimal saldo,
		String estatus) {

}
