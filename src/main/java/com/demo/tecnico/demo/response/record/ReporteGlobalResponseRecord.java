package com.demo.tecnico.demo.response.record;

import java.math.BigDecimal;

public record ReporteGlobalResponseRecord(Integer totalEventos, Long operacionesAplicadas,
		Long operacionesRechazadas, Long operacionesDuplicadas, BigDecimal montoTotalProcesado) {

}
