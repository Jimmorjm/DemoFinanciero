package com.demo.tecnico.demo.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "EVENTOS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_EVENTO")
	private Integer idEvento;
	@ManyToOne
	@JoinColumn(name = "ID_OPERACION")
	private OperacionEntity operacion;
	@Column(name = "FECHA_EVENTO")
	private LocalDateTime fechaEvento;
	@Column(name = "MSG_OPERACION")
	private String msgOperacion;
	@Column(name = "SALDO_INICIAL")
	private BigDecimal saldoInicial;
	@Column(name = "SALDO_FINAL")
	private BigDecimal saldoFinal;
	@Column(name = "STATUS_EVENTO")
	private String statusEvento;

}
