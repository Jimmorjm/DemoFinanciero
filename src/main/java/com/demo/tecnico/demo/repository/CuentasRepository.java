package com.demo.tecnico.demo.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.tecnico.demo.entity.CuentaEntity;

import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;

@Repository
public interface CuentasRepository extends JpaRepository<CuentaEntity, Integer>{

	@Transactional
    @Modifying
	@Query(value = "UPDATE CUENTA c SET c.saldo = :montoSend WHERE c.idCuenta= :idCuentaSend" )
	int actualizaMontoCuenta(@Param("idCuentaSend")Integer idCuenta,@Param("montoSend") BigDecimal monto);



	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT cu FROM CUENTA cu WHERE cu.idCuenta= :idCuentaSend")
	CuentaEntity findByIdLock(@Param("idCuentaSend") Integer idCuenta);

}
