package com.demo.tecnico.demo.model;

import java.math.BigDecimal;

import com.demo.tecnico.demo.enums.EstatusCuentaEnum;

public class Cuenta {

	private String idCuenta;
	private String cliente;
	private BigDecimal saldo;
	private EstatusCuentaEnum estatus;
	private Boolean cuentaEnProcesamiento;

	public Cuenta(String idCuenta, String cliente, BigDecimal saldo, EstatusCuentaEnum estatus) {
		this.idCuenta= idCuenta;
		this.cliente= cliente;
		this.saldo= saldo;
		this.estatus= estatus;
		this.cuentaEnProcesamiento= true;

	}

	public Boolean getCuentaEnProcesamiento() {
		return cuentaEnProcesamiento;
	}

	public void setCuentaEnProcesamiento(Boolean cuentaEnProcesamiento) {
		this.cuentaEnProcesamiento = cuentaEnProcesamiento;
	}

	public String getIdCuenta() {
		return idCuenta;
	}

	public void setIdCuenta(String idCuenta) {
		this.idCuenta = idCuenta;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public EstatusCuentaEnum getEstatus() {
		return estatus;
	}

	public void setEstatus(EstatusCuentaEnum estatus) {
		this.estatus = estatus;
	}

}
