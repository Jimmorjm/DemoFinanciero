package com.demo.tecnico.demo.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "CLIENTES")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_CLIENTE")
	private Integer idCliente;
	@Column(name = "NOMBRE_COMPLETO")
	private String nombreCompleto;
	@Column(name = "DIRECCION")
	private String direccion;
	@Column(name = "NUMERO_CLIENTE")
	private String numeroCliente;
	@Column(name = "FECHA_CREACION")
	private Date fechaCreacion;
	@Column(name = "FECHA_ACTUALIZACION")
	private Date fechaActualizacion;

}
