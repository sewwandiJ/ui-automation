package com.lazada.tests.ui.utils;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;

public class ExcelUtils {
    public XSSFWorkbook workBook;
    XSSFSheet sheet;

    public ExcelUtils(String excelFilePath) {
        try {
            File s = new File(excelFilePath);
            FileInputStream stream = new FileInputStream(s);
            workBook = new XSSFWorkbook(stream);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String getData(int sheetNumber, int row, int column) {
        sheet = workBook.getSheetAt(sheetNumber);
        String data = null;
        DataFormatter formatter = new DataFormatter();
        try {
            data = formatter.formatCellValue(sheet.getRow(row).getCell(column));
        } catch (NullPointerException ignored) {
        }
        return data;
    }

    public int getRowCount(int sheetIndex) {
        int row = workBook.getSheetAt(sheetIndex).getLastRowNum();
        row = row + 1;
        return row;
    }

    public short getLastCellNum(int sheetNumber, int row) {
        sheet = workBook.getSheetAt(sheetNumber);
        return sheet.getRow(row).getLastCellNum();
    }
}
