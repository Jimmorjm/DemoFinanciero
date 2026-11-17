package com.demo.tecnico.demo.service;

import java.util.List;

import com.demo.tecnico.demo.response.record.ReporteFinalResponseRecord;
import com.demo.tecnico.demo.response.record.ReporteGlobalResponseRecord;

public interface ReportService {

	public List<ReporteFinalResponseRecord> generaReporteFinal();

	public ReporteGlobalResponseRecord generaReporteGlobal();

}
