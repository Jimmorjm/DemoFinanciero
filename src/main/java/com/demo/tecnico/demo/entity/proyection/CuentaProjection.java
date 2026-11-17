package com.demo.tecnico.demo.entity.proyection;

import com.demo.tecnico.demo.entity.CuentaEntity;

public interface CuentaProjection {
	CuentaEntity getCuentaTop();
	Long getTotalOperaciones();

}
