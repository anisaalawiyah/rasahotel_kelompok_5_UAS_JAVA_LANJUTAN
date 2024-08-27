package com.javaproject.rasahotel.services.report;

import java.io.IOException;

import com.lowagie.text.Document;

import jakarta.servlet.http.HttpServletResponse;

public interface ReportService {

    byte[] generateReport(String frequency) throws IOException;

    Document generatePdfReport(HttpServletResponse response) throws IOException;
}
