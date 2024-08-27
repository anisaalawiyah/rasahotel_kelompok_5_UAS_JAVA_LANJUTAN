package com.javaproject.rasahotel.controllers.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javaproject.rasahotel.constants.MessageConstant;
import com.javaproject.rasahotel.dto.GeneralResponse;
import com.javaproject.rasahotel.services.report.ReportService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/report")
@Tag(name = "Report Data")
@Slf4j
public class ReportController {

    @Autowired
    ReportService reportService;

    @GetMapping("/report-daily")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Object> reportDaily(HttpServletResponse response) {
        try {
            response.setHeader("Content-Disposition", "Attachment; filename=report.xlsx");
            return ResponseEntity.ok(reportService.generateReport("daily"));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(GeneralResponse.error(MessageConstant.INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/report-monthly")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Object> reportMonthly(HttpServletResponse response) {
        try {
            response.setHeader("Content-Disposition", "Attachment; filename=report.xlsx");
            return ResponseEntity.ok(reportService.generateReport("monthly"));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(GeneralResponse.error(MessageConstant.INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/report-annual")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Object> reportAnnual(HttpServletResponse response) {
        try {
            response.setHeader("Content-Disposition", "Attachment; filename=report.xlsx");
            return ResponseEntity.ok(reportService.generateReport("annual"));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(GeneralResponse.error(MessageConstant.INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/report-pdf")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Object> reportPdf(HttpServletResponse response) {
        try {
            response.setHeader("Content-Disposition", "Attachment; filename=report-booking.pdf");
            return ResponseEntity.ok(reportService.generatePdfReport(response));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(GeneralResponse.error(MessageConstant.INTERNAL_SERVER_ERROR));
        }
    }
}
