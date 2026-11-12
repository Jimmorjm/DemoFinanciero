package com.demo.tecnico.demo.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.demo.tecnico.demo.enums.OperacionEstatusEnum;

public class Operacion {

	private String idOperacion;
	private String idCuenta;
	private Integer idTipoOperacion;
	private BigDecimal monto;
	private LocalDateTime fechaOperacion;
	private OperacionEstatusEnum estatus;
	private String msgOperacion;
	private BigDecimal saldoInicialCuenta;

	public Integer getIdTipoOperacion() {
		return idTipoOperacion;
	}

	public void setIdTipoOperacion(Integer idTipoOperacion) {
		this.idTipoOperacion = idTipoOperacion;
	}

	public Operacion(String idOperacion, String idCuenta, Integer idTipoOperacion, BigDecimal monto) {
		this.idOperacion = idOperacion;
		this.idCuenta = idCuenta;
		this.idTipoOperacion= idTipoOperacion;
		this.monto = monto;
	}

	public String getMsgOperacion() {
		return msgOperacion;
	}

	public void setMsgOperacion(String msgOperacion) {
		this.msgOperacion = msgOperacion;
	}

	public OperacionEstatusEnum getEstatus() {
		return estatus;
	}

	public void setEstatus(OperacionEstatusEnum estatus) {
		this.estatus = estatus;
	}

	public String getIdOperacion() {
		return idOperacion;
	}

	public void setIdOperacion(String idOperacion) {
		this.idOperacion = idOperacion;
	}

	public String getIdCuenta() {
		return idCuenta;
	}

	public void setIdCuenta(String idCuenta) {
		this.idCuenta = idCuenta;
	}

	public BigDecimal getSaldoInicialCuenta() {
		return saldoInicialCuenta;
	}

	public void setSaldoInicialCuenta(BigDecimal saldoInicialCuenta) {
		this.saldoInicialCuenta = saldoInicialCuenta;
	}


	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public LocalDateTime getFechaOperacion() {
		return fechaOperacion;
	}

	public void setFechaOperacion(LocalDateTime localDateTime) {
		this.fechaOperacion = localDateTime;
	}

}
