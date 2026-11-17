package com.demo.tecnico.demo.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.tecnico.demo.entity.CuentaEntity;
import com.demo.tecnico.demo.entity.EventoEntity;
import com.demo.tecnico.demo.enums.EstatusEventoEnum;
import com.demo.tecnico.demo.enums.TipoOperacionEnum;
import com.demo.tecnico.demo.repository.CuentasRepository;
import com.demo.tecnico.demo.repository.EventoRepository;
import com.demo.tecnico.demo.response.record.ReporteFinalResponseRecord;
import com.demo.tecnico.demo.response.record.ReporteGlobalResponseRecord;
import com.demo.tecnico.demo.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private CuentasRepository cuentasRepository;
	@Autowired
	private EventoRepository eventoRepository;

	@Override
	public List<ReporteFinalResponseRecord> generaReporteFinal() {
		List<CuentaEntity> lstCuentas = cuentasRepository.findAll();
		List<ReporteFinalResponseRecord> lstDetalle = lstCuentas.stream().map(cuenta -> {
			cuenta.setLstEventos(eventoRepository.findHistorialCuenta(cuenta.getIdCuenta()));
			ReporteFinalResponseRecord detalle = null;
			if (cuenta.getLstEventos() != null && !cuenta.getLstEventos().isEmpty()) {

				Map<String, Long> mapGroup = cuenta.getLstEventos().stream().collect(Collectors
						.groupingBy(evento -> evento.getOperacion().getTipoOperacion(), Collectors.counting()));

				Map<String, Long> mapStatusOperacion = cuenta.getLstEventos().stream()
						.collect(Collectors.groupingBy(evento -> evento.getStatusEvento(), Collectors.counting()));

				detalle = new ReporteFinalResponseRecord(cuenta.getNumeroCuenta(),
						cuenta.getCliente().getNombreCompleto(),
						obtenSaldoIncial(cuenta.getLstEventos(), cuenta.getSaldo()),

						mapGroup.get(TipoOperacionEnum.OPERACION_ABONO.getNombre()),
						mapGroup.get(TipoOperacionEnum.OPERACION_CARGO.getNombre()), cuenta.getSaldo(),

						mapStatusOperacion.get(EstatusEventoEnum.APROVADA.getNombre()),
						mapStatusOperacion.get(EstatusEventoEnum.RECHAZADA.getNombre()),
						mapStatusOperacion.get(EstatusEventoEnum.DUPLICADA.getNombre()));
			}
			return detalle;
		}).toList();

		return lstDetalle;
	}

	public BigDecimal obtenSaldoIncial(List<EventoEntity> lst, BigDecimal saldo) {
		return lst.stream().reduce(saldo,
				(acumulado,
						eve) -> TipoOperacionEnum.OPERACION_ABONO.getNombre()
								.equals(eve.getOperacion().getTipoOperacion())
										? acumulado.subtract(eve.getOperacion().getMonto())
										: acumulado.add(eve.getOperacion().getMonto()),
				BigDecimal::add);
	}

	@Override
	public ReporteGlobalResponseRecord generaReporteGlobal() {

		List<EventoEntity> list = eventoRepository.findAll();
		Map<String, Long> mapGroup = list.stream()
				.collect(Collectors.groupingBy(evento -> evento.getStatusEvento(), Collectors.counting()));

		ReporteGlobalResponseRecord re = new ReporteGlobalResponseRecord(list.size(),
				mapGroup.get(EstatusEventoEnum.APROVADA.getNombre()),
				mapGroup.get(EstatusEventoEnum.RECHAZADA.getNombre()),
				mapGroup.get(EstatusEventoEnum.DUPLICADA.getNombre()),
				list.stream()
						.filter(eve -> eve.getOperacion().getEstatus().equals(EstatusEventoEnum.APROVADA.getNombre()))
						.map(eve -> eve.getOperacion().getMonto()).reduce(BigDecimal.ZERO, BigDecimal::add));

		return re;
	}

}
