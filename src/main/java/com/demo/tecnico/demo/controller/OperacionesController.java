package com.demo.tecnico.demo.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.tecnico.demo.request.record.OperacionRequestRecord;
import com.demo.tecnico.demo.response.record.OperacionResponseRecord;
import com.demo.tecnico.demo.service.OperacionesServices;
import com.demo.tecnico.demo.service.async.OperacionesAsyncService;

@RestController
@RequestMapping("/operacion")
public class OperacionesController {

	@Autowired
	private OperacionesServices operacionesServices;
	@Autowired
	private OperacionesAsyncService operacionesAsyncService;

	@PostMapping
	public ResponseEntity<OperacionResponseRecord> ejecutaOperacion(@RequestBody OperacionRequestRecord operaion) throws InterruptedException, ExecutionException {
		return ResponseEntity.status(HttpStatus.OK).body(operacionesAsyncService.ejecutaOperacion(operaion).get());
	}

	@PostMapping(path = "/operaciones")
	public ResponseEntity<List<OperacionResponseRecord>> ejecutaOperaciones(@RequestBody List<OperacionRequestRecord> lst) {
		return ResponseEntity.status(HttpStatus.OK).body(operacionesServices.ejecutaOperaciones(lst));
	}



}
