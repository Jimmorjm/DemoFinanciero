package com.demo.tecnico.demo.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.demo.tecnico.demo.request.record.OperacionRequestRecord;

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

@Entity(name = "OPERACION")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperacionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_OPERACION")
	private Integer idOperacion;
	@Column(name = "FOLIO_OPERACION")
	private String folioOperacion;
	@ManyToOne
	@JoinColumn(name = "ID_CUENTA")
	private CuentaEntity cuenta;
	@Column(name = "MONTO")
	private BigDecimal monto;
	@Column(name = "FECHA_OPERACION")
	private LocalDateTime fechaOperacion;
	@Column(name = "ESTATUS_OPERACION")
	private String estatus;
	@Column(name = "TIPO_OPERACION")
	private String tipoOperacion;

	public OperacionEntity(OperacionRequestRecord dto) {
		this.folioOperacion= dto.folioOperacion();
		this.cuenta= new CuentaEntity(dto.idCuenta());
		this.monto= dto.monto();
		this.fechaOperacion= LocalDateTime.now();
		this.tipoOperacion = dto.tipoOperacion();
	}

}
