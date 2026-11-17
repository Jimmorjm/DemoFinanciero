package com.demo.tecnico.demo.service.async;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.tecnico.demo.entity.CuentaEntity;
import com.demo.tecnico.demo.entity.EventoEntity;
import com.demo.tecnico.demo.entity.OperacionEntity;
import com.demo.tecnico.demo.enums.EstatusEventoEnum;
import com.demo.tecnico.demo.enums.OperacionEstatusEnum;
import com.demo.tecnico.demo.enums.TipoOperacionEnum;
import com.demo.tecnico.demo.repository.CuentasRepository;
import com.demo.tecnico.demo.repository.EventoRepository;
import com.demo.tecnico.demo.repository.OperacionesRepository;
import com.demo.tecnico.demo.request.record.OperacionRequestRecord;
import com.demo.tecnico.demo.response.record.OperacionResponseRecord;

@Service
public class OperacionesAsyncService {

	@Autowired
	private OperacionesRepository operacionesRepository;
	@Autowired
	private CuentasRepository cuentasRepository;
	@Autowired
	private EventoRepository eventoRepository;

	@Async("miExecutorPool")
	@Transactional
	public CompletableFuture<OperacionResponseRecord> ejecutaOperacion(OperacionRequestRecord operacion) {
		OperacionEntity entity = null;
		String msgOperacion = "";
		BigDecimal saldoInicial = BigDecimal.ZERO;
		BigDecimal saldoFinal = BigDecimal.ZERO;
		try {
			entity = operacionesRepository.findByFolioOperacion(operacion.folioOperacion());

			if (entity != null && entity.getEstatus() == OperacionEstatusEnum.APLICADA.getNombre()) {
				msgOperacion = "Operacion Duplicada, La operacion: " + operacion.folioOperacion()
						+ " ya se ha procesado.";
				return CompletableFuture.completedFuture(
						returnResponseOpe(EstatusEventoEnum.DUPLICADA.getNombre(), entity.getEstatus(), entity, msgOperacion, null, null));
			}
			entity = entity != null ? requestToEntity(entity, operacion) :new OperacionEntity(operacion) ;

			CuentaEntity cuenta = cuentasRepository.findByIdLock(operacion.idCuenta());
			if (cuenta == null) {
				msgOperacion = "Operacion Rechazada, La cuenta: " + operacion.idCuenta() + " no existe.";
				return CompletableFuture.completedFuture(
						returnResponseOpe(EstatusEventoEnum.RECHAZADA.getNombre(), OperacionEstatusEnum.PENDIENTE.getNombre() , entity, msgOperacion, null, null));
			}
			saldoInicial = cuenta.getSaldo();
			if (operacion.tipoOperacion().equals(TipoOperacionEnum.OPERACION_CARGO.getNombre())) {

				if (cuenta.getSaldo().compareTo(operacion.monto()) < 0) {
					msgOperacion = "Operacion Rechazada, La cuenta: " + operacion.idCuenta()
							+ " no cuenta con fondos suficientes.";
					return CompletableFuture.completedFuture(returnResponseOpe(EstatusEventoEnum.RECHAZADA.getNombre(), OperacionEstatusEnum.PENDIENTE.getNombre(),
							entity, msgOperacion, null, null));
				}
				cuenta = actualizaMontoCuenta(cuenta, operacion.monto(), -1);
				saldoFinal = cuenta.getSaldo();
			} else {
				cuenta = actualizaMontoCuenta(cuenta, operacion.monto(), 1);
			}
			msgOperacion = "Operacion procesada con exito";
			return CompletableFuture.completedFuture(returnResponseOpe(EstatusEventoEnum.APROVADA.getNombre(), OperacionEstatusEnum.APLICADA.getNombre(), entity,
					msgOperacion, saldoInicial, saldoFinal));
		} catch (Exception e) {
			msgOperacion = "Error al procesar la operación: " + e.getMessage();
			return CompletableFuture.completedFuture(
					returnResponseOpe(EstatusEventoEnum.RECHAZADA.getNombre(), OperacionEstatusEnum.PENDIENTE.getNombre(), entity, msgOperacion, null, null));
		}
	}


	private OperacionEntity requestToEntity(OperacionEntity operacion, OperacionRequestRecord request) {
		operacion.setMonto(request.monto());
		operacion.setTipoOperacion(request.tipoOperacion());
		operacion.setCuenta(new CuentaEntity(request.idCuenta()));
		return operacion;
	}

	private OperacionResponseRecord returnResponseOpe(String estatusEvento, String statusOperacion, OperacionEntity operacion, String msgOperacion,
			BigDecimal saldoInicial, BigDecimal saldoFinal) {
		try {
			operacion.setEstatus(statusOperacion);
			operacion = operacionesRepository.save(operacion);
			return entityToResponse(operacion, msgOperacion, estatusEvento);
		} finally {
			generaEvento(operacion, msgOperacion, saldoInicial, saldoFinal, estatusEvento);
		}

	}


	private OperacionResponseRecord entityToResponse(OperacionEntity operacion, String msgOperacion, String statusEvento) {
		return new OperacionResponseRecord(operacion.getIdOperacion(), operacion.getFolioOperacion(),
				operacion.getCuenta().getIdCuenta(), operacion.getMonto(), operacion.getFechaOperacion(),
				operacion.getEstatus(), msgOperacion, statusEvento);
	}

	private CuentaEntity actualizaMontoCuenta(CuentaEntity cuenta, BigDecimal monto, int tipoOpe) {
		if (tipoOpe > 0) {
			cuenta.setSaldo(cuenta.getSaldo().add(monto));
		} else {
			cuenta.setSaldo(cuenta.getSaldo().subtract(monto));
		}
		return cuentasRepository.save(cuenta);
	}

	private void generaEvento(OperacionEntity operacion, String msgOperacion, BigDecimal saldoInicial,
			BigDecimal saldoFinal, String statusEvento) {
		EventoEntity evento = new EventoEntity();
		evento.setFechaEvento(LocalDateTime.now());
		evento.setMsgOperacion(msgOperacion);
		evento.setOperacion(operacion);
		evento.setSaldoInicial(saldoInicial);
		evento.setSaldoFinal(saldoFinal);
		evento.setStatusEvento(statusEvento);
		eventoRepository.save(evento);
	}

}
