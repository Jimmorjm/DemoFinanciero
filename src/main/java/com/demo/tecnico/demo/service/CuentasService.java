package com.demo.tecnico.demo.service;

import java.util.List;

import com.demo.tecnico.demo.response.record.CuentaResponseRecord;
import com.demo.tecnico.demo.response.record.EventoResponseRecord;
import com.demo.tecnico.demo.response.record.TopCuentaResponseRecord;

public interface CuentasService {

	List<CuentaResponseRecord> getAllcuentas();

	List<EventoResponseRecord> getHistorialCuenta(Integer idCuenta);

	List<TopCuentaResponseRecord> obtenTopOperaciones(int topCount);

}
