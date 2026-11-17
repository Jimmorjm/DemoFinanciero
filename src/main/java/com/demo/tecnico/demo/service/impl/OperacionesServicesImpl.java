package com.demo.tecnico.demo.service.impl;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.tecnico.demo.request.record.OperacionRequestRecord;
import com.demo.tecnico.demo.response.record.OperacionResponseRecord;
import com.demo.tecnico.demo.service.OperacionesServices;
import com.demo.tecnico.demo.service.async.OperacionesAsyncService;

@Service
public class OperacionesServicesImpl implements OperacionesServices {

	@Autowired
	private OperacionesAsyncService operacionesAsyncService;

	@Override
	public List<OperacionResponseRecord> ejecutaOperaciones(List<OperacionRequestRecord> lstDtos) {
		List<CompletableFuture<OperacionResponseRecord>> operaciones = lstDtos.stream()
				.map(elemento -> operacionesAsyncService.ejecutaOperacion(elemento)).collect(Collectors.toList());

		return operaciones.stream().map(CompletableFuture::join).collect(Collectors.toList());
	}

}
