package com.demo.tecnico.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.tecnico.demo.model.Cuenta;
import com.demo.tecnico.demo.model.Evento;
import com.demo.tecnico.demo.model.Operacion;
import com.demo.tecnico.demo.model.ReporteFinal;
import com.demo.tecnico.demo.model.ReporteGlobal;
import com.demo.tecnico.demo.service.OperacionesServices;

@RestController
@RequestMapping("/operacion")
public class OperacionesController {

	@Autowired
	private OperacionesServices operacionesServices;

	@PostMapping
	public ResponseEntity<Operacion> ejecutaOperacion(@RequestBody Operacion operaion) {
		operacionesServices.ejecutaOperacion(operaion);
		return ResponseEntity.status(HttpStatus.OK).body(operaion);
	}

	@PostMapping(path = "/operaciones")
	public ResponseEntity<List<Operacion>> ejecutaOperaciones(@RequestBody List<Operacion> lst) {

		lst.stream().forEach(opera -> {
			 operacionesServices.ejecutaOperacion(opera);
		});
		return ResponseEntity.status(HttpStatus.OK).body(lst);
	}

	@GetMapping(path = "/reporteFinal")
	public ResponseEntity<ReporteFinal> generaReporteFinal() {
		return ResponseEntity.status(HttpStatus.OK).body(operacionesServices.generaReporteFinal());
	}

	@GetMapping(path = "/reporteGlobal")
	public ResponseEntity<ReporteGlobal> generaReporteGlobal() {
		return ResponseEntity.status(HttpStatus.OK).body(operacionesServices.generaReporteGlobal());
	}

	@GetMapping("/findTopOperaciones")
	public ResponseEntity<List<Operacion>> findTopOperaciones(@RequestParam int topCount) {
		return ResponseEntity.status(HttpStatus.OK).body(operacionesServices.obtenTopOperaciones(topCount));
	}

	@GetMapping(path = "/findHistorialCuenta")
	public ResponseEntity<List<Evento>> getHistorialCuenta(@RequestParam String idCuenta) {
		return ResponseEntity.status(HttpStatus.OK).body(operacionesServices.getHistorialCuenta(idCuenta));
	}


	@GetMapping(path = "/allCuentas")
	public ResponseEntity<List<Cuenta>> getAllCuentas(){
		return ResponseEntity.status(HttpStatus.OK).body(operacionesServices.getAllcuentas());
	}

	@GetMapping(path = "/allEventos")
	public ResponseEntity<List<Evento>> getAllEventos(){
		return ResponseEntity.status(HttpStatus.OK).body(operacionesServices.getAllEventos());
	}

}
