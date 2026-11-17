package com.demo.tecnico.demo.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.tecnico.demo.entity.EventoEntity;
import com.demo.tecnico.demo.entity.proyection.CuentaProjection;

@Repository
public interface EventoRepository extends JpaRepository<EventoEntity, Integer> {

	@Query(value = "SELECT e FROM EVENTOS e JOIN e.operacion op JOIN op.cuenta cu WHERE cu.idCuenta =:idCuentaSend ORDER BY e.fechaEvento ")
	List<EventoEntity> findHistorialCuenta(@Param("idCuentaSend") Integer idCuenta);

	@Query(value = """
			SELECT e.operacion.cuenta AS cuentaTop, COUNT(DISTINCT e.idEvento) AS totalOperaciones FROM EVENTOS e
			GROUP BY e.operacion.cuenta ORDER BY COUNT(DISTINCT e.idEvento) DESC
					""")
	List<CuentaProjection> findTopCuentas(PageRequest of);


}
