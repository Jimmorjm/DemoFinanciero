package com.demo.tecnico.demo.model;

import java.math.BigDecimal;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class DetalleReporteFinal {

	@CsvBindByName(column = "CUENTA")
	@CsvBindByPosition(position = 1)
	private String cuenta;
	@CsvBindByName(column = "CLIENTE")
	@CsvBindByPosition(position = 2)
	private String cliente;
	@CsvBindByName(column = "SALDO INICIAL")
	@CsvBindByPosition(position = 3)
	private BigDecimal saldoInicial;
	@CsvBindByName(column = "TOTAL ABONOS")
	@CsvBindByPosition(position = 4)
	private Integer totalAbonos;
	@CsvBindByName(column = "TOTAL CARGOS")
	@CsvBindByPosition(position = 5)
	private Integer totalCargos;
	@CsvBindByName(column = "SALDO FINAL")
	@CsvBindByPosition(position = 6)
	private BigDecimal saldoFinal;
	@CsvBindByName(column = "OPERACIONES APLICADAS")
	@CsvBindByPosition(position = 7)
	private Integer operacionesAplicadas;
	@CsvBindByName(column = "OPERACIONES RECHAZADAS")
	@CsvBindByPosition(position = 8)
	private Integer operacionesRechazadas;
	@CsvBindByName(column = "OPERACIONES DUPLICADAS")
	@CsvBindByPosition(position = 9)
	private Integer operacionesDulicadas;

	public String getCuenta() {
		return cuenta;
	}

	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public BigDecimal getSaldoInicial() {
		return saldoInicial;
	}

	public void setSaldoInicial(BigDecimal saldoInicial) {
		this.saldoInicial = saldoInicial;
	}

	public Integer getTotalAbonos() {
		return totalAbonos;
	}

	public void setTotalAbonos(Integer totalAbonos) {
		this.totalAbonos = totalAbonos;
	}

	public Integer getTotalCargos() {
		return totalCargos;
	}

	public void setTotalCargos(Integer totalCargos) {
		this.totalCargos = totalCargos;
	}

	public BigDecimal getSaldoFinal() {
		return saldoFinal;
	}

	public void setSaldoFinal(BigDecimal saldoFinal) {
		this.saldoFinal = saldoFinal;
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

	public Integer getOperacionesDulicadas() {
		return operacionesDulicadas;
	}

	public void setOperacionesDulicadas(Integer operacionesDulicadas) {
		this.operacionesDulicadas = operacionesDulicadas;
	}

}
