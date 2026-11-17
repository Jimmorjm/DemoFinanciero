package com.demo.tecnico.demo.request.record;

import java.math.BigDecimal;

public record OperacionRequestRecord(String folioOperacion, Integer idCuenta, BigDecimal monto, String tipoOperacion) {

}
