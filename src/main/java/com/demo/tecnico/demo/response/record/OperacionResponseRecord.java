package com.demo.tecnico.demo.response.record;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OperacionResponseRecord(Integer idOperacion, String folioOperacion, Integer idCuenta, BigDecimal monto,
		LocalDateTime fechaOperacion, String estatusOperacion, String msgOperacion, String estatusEvento) {

}
