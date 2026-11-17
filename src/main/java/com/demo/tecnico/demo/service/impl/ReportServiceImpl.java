package com.demo.tecnico.demo.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.tecnico.demo.entity.CuentaEntity;
import com.demo.tecnico.demo.entity.EventoEntity;
import com.demo.tecnico.demo.entity.OperacionEntity;
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

						validateValue(mapGroup.get(TipoOperacionEnum.OPERACION_ABONO.getNombre())),
						validateValue(mapGroup.get(TipoOperacionEnum.OPERACION_CARGO.getNombre())),
						cuenta.getSaldo(),
						validateValue(mapStatusOperacion.get(EstatusEventoEnum.APROVADA.getNombre())),
						validateValue(mapStatusOperacion.get(EstatusEventoEnum.RECHAZADA.getNombre())),
						validateValue(mapStatusOperacion.get(EstatusEventoEnum.DUPLICADA.getNombre())));
			} else {
				detalle = new ReporteFinalResponseRecord(cuenta.getNumeroCuenta(),
						cuenta.getCliente().getNombreCompleto(), cuenta.getSaldo(), 0L, 0L, cuenta.getSaldo(), 0L, 0L,
						0L);
			}
			return detalle;
		}).toList();

		return lstDetalle;
	}

	public Long validateValue(Long val) {
		return val == null ? 0L : val;
	}

	public BigDecimal obtenSaldoIncial(List<EventoEntity> lst, BigDecimal saldo) {
		return lst.stream()
				.filter(eve -> eve.getStatusEvento().equals(EstatusEventoEnum.APROVADA.getNombre()))
				.reduce(saldo,
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
		ReporteGlobalResponseRecord re = null;
		if (list != null && !list.isEmpty()) {
			re = new ReporteGlobalResponseRecord(list.size(), mapGroup.get(EstatusEventoEnum.APROVADA.getNombre()),
					validateValue(mapGroup.get(EstatusEventoEnum.RECHAZADA.getNombre())),
					validateValue(mapGroup.get(EstatusEventoEnum.DUPLICADA.getNombre())),

					list.stream().filter(eve -> eve.getStatusEvento().equals(EstatusEventoEnum.APROVADA.getNombre()))
							.map(EventoEntity::getOperacion) // 1. Accedes al subobjeto
							.filter(Objects::nonNull) // 2. Evitas NullPointerException si el subobjeto es nulo
							.map(OperacionEntity::getMonto) // 3. Extraes el BigDecimal
							.filter(Objects::nonNull) // 4. Evitas NullPointerException si el monto es nulo
							.reduce(BigDecimal.ZERO, BigDecimal::add));
		} else {
			re = new ReporteGlobalResponseRecord(0, 0L, 0L, 0L, BigDecimal.ZERO);
		}
		return re;
	}

}
