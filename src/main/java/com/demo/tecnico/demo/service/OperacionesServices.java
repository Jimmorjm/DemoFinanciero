package com.demo.tecnico.demo.service;

import java.util.List;

import com.demo.tecnico.demo.model.Cuenta;
import com.demo.tecnico.demo.model.Evento;
import com.demo.tecnico.demo.model.Operacion;
import com.demo.tecnico.demo.model.ReporteFinal;
import com.demo.tecnico.demo.model.ReporteGlobal;

public interface OperacionesServices {

	void ejecutaOperacion(Operacion operaion);

	ReporteFinal generaReporteFinal();

	ReporteGlobal generaReporteGlobal();

	List<Operacion> obtenTopOperaciones(int topCount);


	List<Evento> getHistorialCuenta(String idCuenta);


	void creaCuentas(int i);

	List<Cuenta> getAllcuentas();

	List<Evento> getAllEventos();



}
