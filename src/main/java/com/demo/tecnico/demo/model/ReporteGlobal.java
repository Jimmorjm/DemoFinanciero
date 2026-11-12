package com.demo.tecnico.demo.model;

import java.math.BigDecimal;

public class ReporteGlobal {

	private Integer totalEventos;
	private Integer operacionesAplicadas;
	private Integer operacionesRechazadas;
	private Integer operacionesDuplicadas;
	private BigDecimal montoTotalProcesado;

	public Integer getTotalEventos() {
		return totalEventos;
	}

	public void setTotalEventos(Integer totalEventos) {
		this.totalEventos = totalEventos;
	}

	public Integer getOperacionesAplicadas() {
		return operacionesAplicadas;
	}

	public void setOperacionesAplicadas(Integer operacionesAplicadas) {
		this.operacionesAplicadas = operacionesAplicadas;
	}

	public Integer getOperacionesRechazadas() {
		return operacionesRechazadas;
	}

	public void setOperacionesRechazadas(Integer operacionesRechazadas) {
		this.operacionesRechazadas = operacionesRechazadas;
	}

	public Integer getOperacionesDuplicadas() {
		return operacionesDuplicadas;
	}

	public void setOperacionesDuplicadas(Integer operacionesDuplicadas) {
		this.operacionesDuplicadas = operacionesDuplicadas;
	}

	public BigDecimal getMontoTotalProcesado() {
		return montoTotalProcesado;
	}

	public void setMontoTotalProcesado(BigDecimal montoTotalProcesado) {
		this.montoTotalProcesado = montoTotalProcesado;
	}

}
