package com.demo.tecnico.demo.model;

import java.util.List;

public class ReporteFinal {

	private List<DetalleReporteFinal> detalleReporteFinal;
	private String fileBase64;

	public List<DetalleReporteFinal> getDetalleReporteFinal() {
		return detalleReporteFinal;
	}

	public void setDetalleReporteFinal(List<DetalleReporteFinal> detalleReporteFinal) {
		this.detalleReporteFinal = detalleReporteFinal;
	}

	public String getFileBase64() {
		return fileBase64;
	}

	public void setFileBase64(String fileBase64) {
		this.fileBase64 = fileBase64;
	}

}
