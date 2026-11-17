package com.demo.tecnico.demo.entity;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity(name = "CUENTA")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CuentaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID_CUENTA")
	private Integer idCuenta;
	@Column(name="NUMERO_CUENTA")
	private String numeroCuenta;
	@ManyToOne
	@JoinColumn(name = "ID_CLIENTE")
	private ClienteEntity cliente;
	@Column(name="SALDO")
	private BigDecimal saldo;
	@Column(name="ESTATUS_CUENTA")
	private String estatus;
	@Transient
	List<EventoEntity> lstEventos;

	public CuentaEntity(Integer idCuenta){
		this.idCuenta= idCuenta;
	}
}
