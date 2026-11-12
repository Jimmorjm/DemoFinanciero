package com.demo.tecnico.demo;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.demo.tecnico.demo.enums.TipoOperacionEnum;
import com.demo.tecnico.demo.model.Operacion;
import com.demo.tecnico.demo.service.OperacionesServices;

@Component
public class GeneraCuentas implements CommandLineRunner {

	@Autowired
	private OperacionesServices operacionesServices;

	@Override
	public void run(String... args) throws Exception {

		operacionesServices.creaCuentas(15);

		//Operaciones validas
		System.out.println("Operaciones validas");
		for (int j = 1; j <= 10; j++) {
			if (j <= 5) {
				Operacion op = new Operacion("OP00" + j, "CU000" + j, TipoOperacionEnum.OPERACION_CARGO.getId(), new BigDecimal("500.0"));
				operacionesServices.ejecutaOperacion(op);
			} else {
				Operacion op = new Operacion("OP00" + j, "CU000" + j, TipoOperacionEnum.OPERACION_ABONO.getId(), new BigDecimal("500.0"));
				operacionesServices.ejecutaOperacion(op);
			}
		}


		//Operaciones Duplicadas
		System.out.println("Operaciones Duplicadas");
		for (int j = 1; j <= 10; j++) {
			if (j <= 5) {
				Operacion op = new Operacion("OP00" + j, "CU000" + j, TipoOperacionEnum.OPERACION_CARGO.getId(), new BigDecimal("500.0"));
				operacionesServices.ejecutaOperacion(op);
			} else {
				Operacion op = new Operacion("OP00" + j, "CU000" + j, TipoOperacionEnum.OPERACION_ABONO.getId(), new BigDecimal("500.0"));
				operacionesServices.ejecutaOperacion(op);
			}
		}

		// Cuentas inexistentes
		System.out.println("Cuentas inexistentes");
		for (int j = 1; j <= 10; j++) {
			Operacion op = new Operacion("OP00" + j+50, "CU000" + j + 50, TipoOperacionEnum.OPERACION_CARGO.getId(), new BigDecimal("500.0"));
			operacionesServices.ejecutaOperacion(op);
		}

		// Saldo insuficiente
		System.out.println("Saldo insuficiente");
		for (int j = 1; j <= 5; j++) {
			Operacion op = new Operacion("OP00" + j, "CU000" + j, TipoOperacionEnum.OPERACION_CARGO.getId(), new BigDecimal("20000.0"));
			operacionesServices.ejecutaOperacion(op);
		}

		System.out.println("Eventos "+operacionesServices.getAllEventos().size());



	}

}
