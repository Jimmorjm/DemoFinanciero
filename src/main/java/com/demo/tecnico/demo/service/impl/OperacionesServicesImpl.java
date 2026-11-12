package com.demo.tecnico.demo.service.impl;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.demo.tecnico.demo.enums.EstatusCuentaEnum;
import com.demo.tecnico.demo.enums.OperacionEstatusEnum;
import com.demo.tecnico.demo.enums.TipoOperacionEnum;
import com.demo.tecnico.demo.model.Cuenta;
import com.demo.tecnico.demo.model.DetalleReporteFinal;
import com.demo.tecnico.demo.model.Evento;
import com.demo.tecnico.demo.model.Operacion;
import com.demo.tecnico.demo.model.ReporteFinal;
import com.demo.tecnico.demo.model.ReporteGlobal;
import com.demo.tecnico.demo.service.OperacionesServices;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

@Service
public class OperacionesServicesImpl implements OperacionesServices {

	private final Object operacionesLock = new Object();

	private List<Evento> lstEventosRepository = new ArrayList<>();

	private List<Cuenta> lstCuentasRepository = new ArrayList<>();

	@Override
	@Async("miExecutorPool")
	public void ejecutaOperacion(Operacion operacion) {

		synchronized (operacionesLock) {

			try {

				if (validaOperacion(operacion.getIdOperacion())) {
					operacion.setEstatus(OperacionEstatusEnum.DUPLICADA);
					operacion.setMsgOperacion("La operacion: " + operacion.getIdOperacion() + " ya se ha procesado.");
					return;
				}

				Cuenta cuenta = getCuenta(operacion);

				if (cuenta == null) {
					return;
				}

				operacion.setSaldoInicialCuenta(cuenta.getSaldo());

				if (operacion.getIdTipoOperacion().equals(TipoOperacionEnum.OPERACION_CARGO.getId())) {

					if (cuenta.getSaldo().compareTo(operacion.getMonto()) > 0 ) {

						operacion.setEstatus(OperacionEstatusEnum.RECHAZADA);
						operacion.setMsgOperacion(
								"La cuenta: " + operacion.getIdCuenta() + " no cuenta con fondos suficientes.");
						return;

					}

					actualizaMontoCuenta(cuenta.getIdCuenta(), operacion.getMonto(), -1);
					operacion.setEstatus(OperacionEstatusEnum.APLICADA);

				} else {

					actualizaMontoCuenta(cuenta.getIdCuenta(), operacion.getMonto(), 1);
					operacion.setEstatus(OperacionEstatusEnum.APLICADA);
				}

			} catch (Exception e) {
				operacion.setEstatus(OperacionEstatusEnum.RECHAZADA);
				operacion.setMsgOperacion("Error al procesar la operación: " + e.getMessage());

			} finally {
				operacion.setFechaOperacion(LocalDateTime.now());
				generaEvento(operacion);
			}
		}
	}

	private Boolean validaOperacion(String operacion) {

		return getLstEventosRepository().stream().filter(e -> e.getOperacion() != null)
				.filter(e -> e.getOperacion().getIdOperacion() != null)
				.filter(e -> e.getOperacion().getIdOperacion().equals(operacion))
				.anyMatch(e -> e.getOperacion().getEstatus() != null
						&& e.getOperacion().getEstatus().getId().equals(OperacionEstatusEnum.APLICADA.getId()));
	}

	private Cuenta actualizaMontoCuenta(String idCuenta, BigDecimal monto, int tipoOpe) {

		Cuenta cuenta = getLstCuentasRepository().stream().filter(c -> c.getIdCuenta().equals(idCuenta)).findFirst()
				.orElse(null);

		if (cuenta != null) {

			if (tipoOpe > 0) {
				cuenta.setSaldo(cuenta.getSaldo().add(monto));
			} else {
				cuenta.setSaldo(cuenta.getSaldo().subtract(monto));
			}
		}

		return cuenta;
	}

	private Cuenta getCuenta(Operacion operacion) {

		Cuenta cuenta = getLstCuentasRepository().stream().filter(c -> c.getIdCuenta().equals(operacion.getIdCuenta()))
				.findFirst().orElse(null);
		if (cuenta == null) {
			operacion.setEstatus(OperacionEstatusEnum.RECHAZADA);
			operacion.setMsgOperacion("La cuenta: " + operacion.getIdCuenta() + " no existe.");
			return null;
		} else if (cuenta.getEstatus() == null
				|| cuenta.getEstatus().getId().equals(EstatusCuentaEnum.ESTATUS_BLOQUEADA.getId())) {
			operacion.setEstatus(OperacionEstatusEnum.RECHAZADA);
			operacion.setMsgOperacion("La cuenta: " + operacion.getIdCuenta() + " se encuentra bloqueada.");
			return null;
		}

		return cuenta;
	}

	private void generaEvento(Operacion operacion) {

		Evento evento = new Evento();

		evento.setIdEvento(getNextId());
		evento.setFechaRecepcion(LocalDateTime.now());
		evento.setOperacion(operacion);

		lstEventosRepository.add(evento);
	}

	private Integer getNextId() {
		Evento evento = lstEventosRepository.stream().max(Comparator.comparingInt(Evento::getIdEvento)).orElse(null);
		return evento != null ? evento.getIdEvento() + 1 : 1;
	}

	public List<Evento> getLstEventosRepository() {
		if (lstEventosRepository == null) {
			lstEventosRepository = new ArrayList<>();
		}
		return lstEventosRepository;
	}

	public void setLstEventosRepository(List<Evento> lstEventosRepository) {
		this.lstEventosRepository = lstEventosRepository;
	}

	public List<Cuenta> getLstCuentasRepository() {
		if (lstCuentasRepository == null) {
			lstCuentasRepository = new ArrayList<>();
		}
		return lstCuentasRepository;
	}

	public void setLstCuentasRepository(List<Cuenta> lstCuentasRepository) {
		this.lstCuentasRepository = lstCuentasRepository;
	}

	@Override
	public ReporteFinal generaReporteFinal() {

		ReporteFinal re = new ReporteFinal();
		re.setDetalleReporteFinal(new ArrayList<>());

		Map<String, List<Evento>> mapEventos = getLstEventosRepository().stream()
				.collect(Collectors.groupingBy(e -> e.getOperacion().getIdCuenta()));

		mapEventos.forEach((clave, valor) -> {

			valor = valor.stream().filter(e -> e.getOperacion().getFechaOperacion() != null)
					.sorted(Comparator.comparing(evento -> evento.getOperacion().getFechaOperacion()))
					.collect(Collectors.toList());

			if (valor.isEmpty()) {
				return;
			}

			DetalleReporteFinal detalle = new DetalleReporteFinal();

			Cuenta cu = getLstCuentasRepository().stream()
					.filter(f -> f.getIdCuenta() != null && f.getIdCuenta().equals(clave)).findFirst().orElse(null);

			if (cu == null) {
				detalle.setCuenta(clave);
			} else {
				detalle.setCliente(cu.getCliente());
				detalle.setCuenta(cu.getIdCuenta());
				detalle.setSaldoFinal(cu.getSaldo());
			}

			detalle.setSaldoInicial(valor.get(0).getOperacion().getSaldoInicialCuenta());



			Map<Integer, Long> conteoPorTipo = valor.stream().filter(e -> e.getOperacion().getIdTipoOperacion() != null)
					.collect(Collectors.groupingBy(e -> e.getOperacion().getIdTipoOperacion(), Collectors.counting()));

			detalle.setTotalAbonos(
					conteoPorTipo.getOrDefault(TipoOperacionEnum.OPERACION_ABONO.getId(), 0L).intValue());

			detalle.setTotalCargos(
					conteoPorTipo.getOrDefault(TipoOperacionEnum.OPERACION_CARGO.getId(), 0L).intValue());

			Map<Integer, Long> conteoPorOperacion = valor.stream().filter(e -> e.getOperacion().getEstatus() != null)
					.filter(e -> e.getOperacion().getEstatus().getId() != null)
					.collect(Collectors.groupingBy(e -> e.getOperacion().getEstatus().getId(), Collectors.counting()));

			detalle.setOperacionesAplicadas(
					conteoPorOperacion.getOrDefault(OperacionEstatusEnum.APLICADA.getId(), 0L).intValue());

			detalle.setOperacionesRechazadas(
					conteoPorOperacion.getOrDefault(OperacionEstatusEnum.RECHAZADA.getId(), 0L).intValue());

			detalle.setOperacionesDulicadas(
					conteoPorOperacion.getOrDefault(OperacionEstatusEnum.DUPLICADA.getId(), 0L).intValue());

			re.getDetalleReporteFinal().add(detalle);
		});

		re.setFileBase64(creaArchivoMemoria(re.getDetalleReporteFinal()));

		return re;
	}

	public <T> String creaArchivoMemoria(List<T> lstDto) {
		try (StringWriter writer = new StringWriter()) {
			StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(writer).build();
			beanToCsv.write(lstDto);
			byte[] bytesCsv = writer.toString().getBytes(StandardCharsets.UTF_8);
			return Base64.getEncoder().encodeToString(bytesCsv);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public ReporteGlobal generaReporteGlobal() {
		ReporteGlobal re = new ReporteGlobal();
		re.setTotalEventos(getLstEventosRepository().size());
		Map<Integer, Long> conteoPorOperacion = getLstEventosRepository().stream()
				.collect(Collectors.groupingBy(eve -> eve.getOperacion().getEstatus().getId(), Collectors.counting()));
		re.setOperacionesAplicadas(conteoPorOperacion.get(OperacionEstatusEnum.APLICADA.getId()).intValue());
		re.setOperacionesRechazadas(conteoPorOperacion.get(OperacionEstatusEnum.RECHAZADA.getId()).intValue());
		re.setOperacionesDuplicadas(conteoPorOperacion.get(OperacionEstatusEnum.DUPLICADA.getId()).intValue());

		re.setMontoTotalProcesado(getLstEventosRepository().stream()
		        .filter(eve -> eve.getOperacion().getEstatus().getId().equals(OperacionEstatusEnum.APLICADA.getId()))
		        .map(eve -> eve.getOperacion().getMonto())
		        .reduce(BigDecimal.ZERO, BigDecimal::add));

		return re;
	}

	@Override
	public List<Operacion> obtenTopOperaciones(int topCount) {
		return getLstEventosRepository().stream().collect(Collectors.groupingBy(e -> e.getOperacion().getIdCuenta()))
				.entrySet().stream().sorted((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size()))
				.limit(topCount).flatMap(entry -> entry.getValue().stream()).collect(Collectors.toList()).stream()
				.map(ev -> ev.getOperacion()).toList();
	}

	@Override
	public List<Evento> getHistorialCuenta(String idCuenta) {
		return getLstEventosRepository().stream().filter(eve -> eve.getOperacion().getIdCuenta().equals(idCuenta))
				.collect(Collectors.toList()).stream()
				.sorted(Comparator.comparing(evento -> evento.getOperacion().getFechaOperacion())).toList();
	}

	@Override
	public void creaCuentas(int i) {
		double min = 3000.0;
		double max = 15000.0;
		Random r = new Random();
		for (int j = 1; j <= i; j++) {
			Cuenta cu = new Cuenta("CU000" + j, "Cliente 00" + j, new BigDecimal(min + (max - min) * r.nextDouble()),
					EstatusCuentaEnum.ESTATUS_ACTIVO);
			getLstCuentasRepository().add(cu);
		}

	}

	@Override
	public List<Cuenta> getAllcuentas() {
		return getLstCuentasRepository();
	}

	@Override
	public List<Evento> getAllEventos() {
		return getLstEventosRepository();
	}

}
