package com.demo.tecnico.demo.response.record;

import java.math.BigDecimal;

public record ReporteFinalResponseRecord(String cuenta, String cliente, BigDecimal saldoInicial, Long totalAbonos,
		Long totalCargos, BigDecimal saldoFinal, Long operacionesAplicadas, Long operacionesRechazadas,
		Long operacionesDulicadas) {

}
