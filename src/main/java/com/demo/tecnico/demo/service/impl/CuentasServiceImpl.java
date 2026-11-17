package com.demo.tecnico.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.demo.tecnico.demo.entity.CuentaEntity;
import com.demo.tecnico.demo.entity.EventoEntity;
import com.demo.tecnico.demo.entity.proyection.CuentaProjection;
import com.demo.tecnico.demo.repository.CuentasRepository;
import com.demo.tecnico.demo.repository.EventoRepository;
import com.demo.tecnico.demo.response.record.CuentaResponseRecord;
import com.demo.tecnico.demo.response.record.EventoResponseRecord;
import com.demo.tecnico.demo.response.record.OperacionResponseRecord;
import com.demo.tecnico.demo.response.record.TopCuentaResponseRecord;
import com.demo.tecnico.demo.service.CuentasService;

@Service
public class CuentasServiceImpl implements CuentasService {

	@Autowired
	private CuentasRepository cuentasRepository;

	@Autowired
	private EventoRepository eventoRepository;

	@Override
	public List<CuentaResponseRecord> getAllcuentas() {
		List<CuentaEntity> lst = cuentasRepository.findAll();
		List<CuentaResponseRecord> lstReturn = null;
		if (lst != null && !lst.isEmpty()) {
			lstReturn = lst.stream()
					.map(cuenta -> new CuentaResponseRecord(cuenta.getIdCuenta(), cuenta.getCliente().getIdCliente(),
							cuenta.getNumeroCuenta(), cuenta.getSaldo(), cuenta.getEstatus()))
					.toList();
		}
		return lstReturn;
	}

	@Override
	public List<EventoResponseRecord> getHistorialCuenta(Integer idCuenta) {
		List<EventoEntity> lst = eventoRepository.findHistorialCuenta(idCuenta);
		List<EventoResponseRecord> lstReturn = null;
		if (lst != null && !lst.isEmpty()) {
			lstReturn = lst.stream().map(eve -> returnEvento(eve)).toList();
		}
		return lstReturn;
	}

	private EventoResponseRecord returnEvento(EventoEntity evento) {
		return new EventoResponseRecord(evento.getIdEvento(),
				new OperacionResponseRecord(evento.getOperacion().getIdOperacion(),
						evento.getOperacion().getFolioOperacion(), evento.getOperacion().getCuenta().getIdCuenta(),
						evento.getOperacion().getMonto(), evento.getOperacion().getFechaOperacion(),
						evento.getOperacion().getEstatus(), evento.getMsgOperacion(),null),
				evento.getFechaEvento(), evento.getMsgOperacion(), evento.getSaldoInicial(), evento.getSaldoFinal(), evento.getStatusEvento());

	}

	@Override
	public List<TopCuentaResponseRecord> obtenTopOperaciones(int topCount) {
		List<CuentaProjection> lst = eventoRepository.findTopCuentas(PageRequest.of(0, topCount));
		List<TopCuentaResponseRecord> lstReturn = null;
		if (lst != null && !lst.isEmpty()) {
			lstReturn = lst.stream().map(topCu -> new TopCuentaResponseRecord(
					new CuentaResponseRecord(topCu.getCuentaTop().getIdCuenta(),
							topCu.getCuentaTop().getCliente().getIdCliente(), topCu.getCuentaTop().getNumeroCuenta(),
							topCu.getCuentaTop().getSaldo(), topCu.getCuentaTop().getEstatus()),
					topCu.getTotalOperaciones())).toList();
		}
		return lstReturn;
	}
}
