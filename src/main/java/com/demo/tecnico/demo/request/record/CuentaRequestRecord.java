package com.demo.tecnico.demo.request.record;

import java.math.BigDecimal;

public record CuentaRequestRecord(Integer idCliente, String numeroCuenta, BigDecimal saldo, String estatus) {

}
