package com.demo.tecnico.demo.response.record;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EventoResponseRecord(Integer idEvento, OperacionResponseRecord operacion, LocalDateTime fechaEvento,
		String msgOperacion, BigDecimal saldoInicial, BigDecimal saldoFinal, String statusEvento) {

}
