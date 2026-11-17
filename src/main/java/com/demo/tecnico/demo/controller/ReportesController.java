package com.demo.tecnico.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.tecnico.demo.response.record.ReporteFinalResponseRecord;
import com.demo.tecnico.demo.response.record.ReporteGlobalResponseRecord;
import com.demo.tecnico.demo.service.ReportService;


@RestController
@RequestMapping("/reportes")
public class ReportesController {

	@Autowired
	private ReportService reportService;

	@GetMapping(path = "/reporteFinal")
	public ResponseEntity<List<ReporteFinalResponseRecord>> generaReporteFinal() {
		return ResponseEntity.status(HttpStatus.OK).body(reportService.generaReporteFinal());
	}

	@GetMapping(path = "/reporteGlobal")
	public ResponseEntity<ReporteGlobalResponseRecord> generaReporteGlobal() {
		return ResponseEntity.status(HttpStatus.OK).body(reportService.generaReporteGlobal());
	}

}
