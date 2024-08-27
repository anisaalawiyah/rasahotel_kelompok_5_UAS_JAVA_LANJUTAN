package com.javaproject.rasahotel.services.report;

import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.io.ByteArrayOutputStream;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaproject.rasahotel.entities.BookedRoom;
import com.javaproject.rasahotel.repositories.BookingRepository;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class ReportServicelmpl implements ReportService {

    @Autowired
    BookingRepository bookingRepository;

    private NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    @Override
    public byte[] generateReport(String frequency) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Sheet sheet = workbook.createSheet();

        CellStyle titleCellStyle = createCellStyle(workbook, true, false, false);
        CellStyle headerCellStyle = createCellStyle(workbook, false, true, false);
        CellStyle tableCellStyle = createCellStyle(workbook, false, false, true);

        generateTitle(sheet, titleCellStyle);
        generateHeader(sheet, headerCellStyle);

        List<BookedRoom> booking = bookingRepository.findAll();
        LocalDate date = LocalDate.now();
        if (booking.isEmpty()) {
            generateEmptyData(sheet, tableCellStyle);
        } else {
            int currentRowIndex = 3;
            int rowNum = 1;
            for (BookedRoom room : booking) {
                Row row = sheet.createRow(currentRowIndex);
                if (room.getCheckInDate().getDayOfMonth()==date.getDayOfMonth() && frequency.equals("daily"))
                    generateTabelData(row, room, tableCellStyle, rowNum);
                if (room.getCheckInDate().getMonth().getValue()==date.getMonth().getValue() && frequency.equals("monthly"))
                    generateTabelData(row, room, tableCellStyle, rowNum);
                if (room.getCheckInDate().getYear()==date.getYear() && frequency.equals("annual"))
                    generateTabelData(row, room, tableCellStyle, rowNum);
                currentRowIndex++;
                rowNum++;
            }
        }

        for (int i = 0; i < 7; i++) {
            sheet.autoSizeColumn(i);
        }

        workbook.write(byteArrayOutputStream);
        workbook.close();

        return byteArrayOutputStream.toByteArray();
    }

    private CellStyle createCellStyle(Workbook workbook,
            boolean isHeader, boolean isTitle, boolean isTable) {
        CellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");

        if (isHeader) {
            font.setFontHeightInPoints((short) 20);
            font.setBold(true);
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        }

        if (isTitle) {
            font.setFontHeightInPoints((short) 12);
            font.setBold(true);
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        }

        if (isTable) {
            font.setFontHeightInPoints((short) 12);
            cellStyle.setAlignment(HorizontalAlignment.LEFT);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
        }

        cellStyle.setFont(font);
        return cellStyle;
    }

    private void generateTitle(Sheet sheet, CellStyle cellStyle) {
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("LAPORAN DATA BOOKING RASA HOTEL");
        titleCell.setCellStyle(cellStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));
    }

    private void generateHeader(Sheet sheet, CellStyle cellStyle) {
        Row headerRow = sheet.createRow(2);
        int currentCellIndex = 0;
        headerRow.createCell(currentCellIndex).setCellValue("No");
        headerRow.createCell(++currentCellIndex).setCellValue("Name");
        headerRow.createCell(++currentCellIndex).setCellValue("Email");
        headerRow.createCell(++currentCellIndex).setCellValue("Room Name");
        headerRow.createCell(++currentCellIndex).setCellValue("CheckIn Date");
        headerRow.createCell(++currentCellIndex).setCellValue("CheckOut Date");
        headerRow.createCell(++currentCellIndex).setCellValue("Total");
        for (int i = 0; i < currentCellIndex + 1; i++) {
            headerRow.getCell(i).setCellStyle(cellStyle);
        }
    }

    private void generateEmptyData(Sheet sheet, CellStyle cellStyle) {
        Row emptyRow = sheet.createRow(3);
        emptyRow.createCell(0).setCellValue("No Data");
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 7));
        emptyRow.getCell(0).setCellStyle(cellStyle);
    }

    private void generateTabelData(Row row, BookedRoom booking, CellStyle cellStyle,
            int rowNum) {
        int currentCellIndex = 0;

        Cell numberRow = row.createCell(currentCellIndex);
        numberRow.setCellValue(rowNum);
        numberRow.setCellStyle(cellStyle);

        Cell nameRow = row.createCell(++currentCellIndex);
        nameRow.setCellValue(booking.getCustomer().getName());
        nameRow.setCellStyle(cellStyle);

        Cell emailRow = row.createCell(++currentCellIndex);
        emailRow.setCellValue(booking.getCustomer().getEmail());
        emailRow.setCellStyle(cellStyle);

        Cell roomRow = row.createCell(++currentCellIndex);
        roomRow.setCellValue(booking.getRoom().getName());
        roomRow.setCellStyle(cellStyle);

        Cell checkInRow = row.createCell(++currentCellIndex);
        checkInRow.setCellValue(booking.getCheckInDate().format(DateTimeFormatter.ISO_DATE));
        checkInRow.setCellStyle(cellStyle);

        Cell checkOutRow = row.createCell(++currentCellIndex);
        checkOutRow.setCellValue(booking.getCheckOutDate().format(DateTimeFormatter.ISO_DATE));
        checkOutRow.setCellStyle(cellStyle);

        Cell totalRow = row.createCell(++currentCellIndex);
        totalRow.setCellValue(formatter.format(booking.getTotalPayment()));
        totalRow.setCellStyle(cellStyle);
    }

    @Override
    public Document generatePdfReport(HttpServletResponse response) throws IOException, DocumentException {

        Document document = new Document(PageSize.A4);

        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        Font fontTiltle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        fontTiltle.setSize(20);
        Paragraph paragraph1 = new Paragraph("Laporan Data Booking RASA HOTEL", fontTiltle);
        paragraph1.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(paragraph1);

        document.add(new Paragraph("\n"));

        PdfPTable table = new PdfPTable(7);

        table.setWidthPercentage(100f);
        table.setSpacingBefore(5);

        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(CMYKColor.WHITE);
        // cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        font.setColor(CMYKColor.BLACK);

        // Table
        table.addCell(new Phrase("No", font));
        table.addCell(new Phrase("Name", font));
        table.addCell(new Phrase("Email", font));
        table.addCell(new Phrase("Room Name", font));
        table.addCell(new Phrase("CheckIn Date", font));
        table.addCell(new Phrase("CheckOut Date", font));
        table.addCell(new Phrase("Total Payment", font));
        table.completeRow();

        List<BookedRoom> bookings = bookingRepository.findAll();
        int cellNum = 1;
        if (bookings.isEmpty()) {
            table.addCell(String.valueOf("Data Kosong"));
            table.completeRow();
        } else
            for (BookedRoom booked : bookings) {
                table.addCell(String.valueOf(cellNum));
                table.addCell(booked.getCustomer().getName());
                table.addCell(booked.getCustomer().getEmail());
                table.addCell(booked.getRoom().getName());
                table.addCell(booked.getCheckInDate().format(DateTimeFormatter.ISO_DATE));
                table.addCell(booked.getCheckOutDate().format(DateTimeFormatter.ISO_DATE));
                table.addCell(formatter.format(booked.getTotalPayment()));
                table.completeRow();
                cellNum++;
            }

        document.add(table);

        document.close();
        return document;
    }
}
