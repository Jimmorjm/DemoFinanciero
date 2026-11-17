package com.demo.tecnico.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.tecnico.demo.entity.OperacionEntity;

import jakarta.persistence.LockModeType;

@Repository
public interface OperacionesRepository extends JpaRepository<OperacionEntity, Integer>{

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT o FROM OPERACION o WHERE o.folioOperacion =:folioSend")
	OperacionEntity findByFolioOperacion(@Param("folioSend") String folioOperacion);

}
