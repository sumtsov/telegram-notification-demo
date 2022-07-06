package com.dsumtsov.telegram.notification.demo.service.impl;

import com.dsumtsov.telegram.notification.demo.domain.Document;
import com.dsumtsov.telegram.notification.demo.enums.ReportHeaderTitle;
import com.dsumtsov.telegram.notification.demo.service.ReportService;
import com.dsumtsov.telegram.notification.demo.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
@Service
public class ReportServiceImpl implements ReportService {

    private static final String REPORT_NAME = "Orders-%s.xlsx";

    @Override
    public Document getReport() {
        try (var book = new XSSFWorkbook();
             var out = new ByteArrayOutputStream()) {
            Sheet sheet = book.createSheet("Orders");
            createHeader(book, sheet);
            createEmptyRow(sheet);
            book.write(out);

            return new Document(
                    String.format(REPORT_NAME, DateUtils.getCurrentDateTimeFormatted()),
                    out.toByteArray()
            );
        } catch (IOException e) {
            log.error("Failed to create report; error message: {}", e.getMessage());
            throw new IllegalStateException("Failed to create report", e);
        }
    }

    private void createHeader(XSSFWorkbook book,
                              Sheet sheet) {
        Row header = sheet.createRow(0);
        header.setRowStyle(createHeaderStyle(book));
        header.createCell(ReportHeaderTitle.ID.index, CellType.STRING)
                .setCellValue(ReportHeaderTitle.ID.title);
        header.createCell(ReportHeaderTitle.SELLER_ID.index, CellType.STRING)
                .setCellValue(ReportHeaderTitle.SELLER_ID.title);
        header.createCell(ReportHeaderTitle.SKU_ID.index, CellType.STRING)
                .setCellValue(ReportHeaderTitle.SKU_ID.title);
        header.createCell(ReportHeaderTitle.QUANTITY.index, CellType.STRING)
                .setCellValue(ReportHeaderTitle.QUANTITY.title);
    }

    private void createEmptyRow(Sheet sheet) {
        Row row = sheet.createRow(1);
        row.createCell(ReportHeaderTitle.ID.index, CellType.STRING).setCellValue(StringUtils.EMPTY);
        row.createCell(ReportHeaderTitle.SELLER_ID.index, CellType.STRING).setCellValue(StringUtils.EMPTY);
        row.createCell(ReportHeaderTitle.SKU_ID.index, CellType.STRING).setCellValue(StringUtils.EMPTY);
        row.createCell(ReportHeaderTitle.QUANTITY.index, CellType.STRING).setCellValue(StringUtils.EMPTY);
    }

    private XSSFCellStyle createHeaderStyle(XSSFWorkbook book) {
        XSSFCellStyle headerStyle = book.createCellStyle();
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        XSSFFont headerFont = book.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        return headerStyle;
    }
}
