package com.demo.tecnico.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.tecnico.demo.response.record.CuentaResponseRecord;
import com.demo.tecnico.demo.response.record.EventoResponseRecord;
import com.demo.tecnico.demo.response.record.TopCuentaResponseRecord;
import com.demo.tecnico.demo.service.CuentasService;

@RestController
@RequestMapping("/cuenta")
public class CuentasController {

	@Autowired
	private CuentasService cuentasService;

	@GetMapping
	public ResponseEntity<List<CuentaResponseRecord>> getAllCuentas() {
		return ResponseEntity.status(HttpStatus.OK).body(cuentasService.getAllcuentas());
	}

	@GetMapping(path = "/findHistorialCuenta")
	public ResponseEntity<List<EventoResponseRecord>> getHistorialCuenta(@RequestParam Integer idCuenta) {
		return ResponseEntity.status(HttpStatus.OK).body(cuentasService.getHistorialCuenta(idCuenta));
	}

	@GetMapping("/findTopCuentas")
	public ResponseEntity<List<TopCuentaResponseRecord>> findTopOperaciones(@RequestParam int topCount) {
		return ResponseEntity.status(HttpStatus.OK).body(cuentasService.obtenTopOperaciones(topCount));
	}

}
