package com.demo.tecnico.demo.service;

import java.util.List;

import com.demo.tecnico.demo.request.record.OperacionRequestRecord;
import com.demo.tecnico.demo.response.record.OperacionResponseRecord;

public interface OperacionesServices {

	public List<OperacionResponseRecord> ejecutaOperaciones(List<OperacionRequestRecord> lstDtos);

}
