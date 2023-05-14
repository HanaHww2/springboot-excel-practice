package me.study.excelpractice.common.excel;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import me.study.excelpractice.common.exception.CustomApiException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.springframework.http.HttpStatus;

public class SimpleExcelFile<T> implements ExcelFile<T> {
    private Workbook workbook;
    private Sheet sheet;
    private int rowIndex = 0;
    private ExcelColumnInfo[] columnInfos;
    private Color headerColor;
    private Map<CellType, CellStyle> cellStyleMap = new EnumMap<>(CellType.class);

    public SimpleExcelFile() {
        this.setExcelFile(CellColor.COLOR_NONE);
    }

    public SimpleExcelFile(Color headerColor) {
        this.setExcelFile(headerColor);
    }

    private void setExcelFile(Color headerColor) {
        // SXSSFWorkbook 클래스는 100개 행(디폴트 설정)씩 잘라서 파일을 생성하며, 이를 위해 임시 파일을 만든다.
        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook();
        this.workbook = sxssfWorkbook;
        this.sheet = workbook.createSheet();
        this.headerColor = headerColor;
    }

    public <E extends Enum<E> & ExcelColumnInfo> void setMetaInfo(Class<E> excelInfoClazz) {
        this.columnInfos = excelInfoClazz.getEnumConstants();
        for (ExcelColumnInfo val : this.columnInfos) {
            val.getHeaderName();
        }
    }

    public void renderHeader() {
        Row headerRow = sheet.createRow(rowIndex++);
        for (ExcelColumnInfo columnInfo : this.columnInfos) {
            Cell headerCell = headerRow.createCell(columnInfo.getOrder());
            sheet.setColumnWidth(columnInfo.getOrder(), columnInfo.getWidth());
            CellStyle typedStyle = getTypedStyle(CellType.HEADER, this.headerColor);
            headerCell.setCellStyle(typedStyle);
            headerCell.setCellValue(columnInfo.getHeaderName());
        }
    }

    public void renderBody(List<T> dtoList) {
        for (Object dto: dtoList) {
            Row row = sheet.createRow(rowIndex++);
            for (ExcelColumnInfo columnInfo : this.columnInfos) {
                Cell cell;
                try {
                    cell = row.createCell(columnInfo.getOrder());
                    Field field = dto.getClass().getDeclaredField(columnInfo.getAttributeName());
                    field.setAccessible(true);

                    CellType cellType = columnInfo.getCellType();

                    CellStyle typedStyle = getTypedStyle(cellType);
                    cell.setCellStyle(typedStyle);
                    Object val = field.get(dto);
                    if (val == null) {
                        continue;
                    }
                    cellType.applyCellValue(cell, field.get(dto));
                } catch (Exception e) {
                    // 요청은 정상적이나, 코드 내 타입 변환 문제 등
                    throw new CustomApiException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
    }

    public void write(OutputStream stream) throws IOException {
        try {
            workbook.write(stream);
        } finally {
            stream.close();
            this.close();
        }
    }
    public void close() throws IOException {
        workbook.close();
        ((SXSSFWorkbook) workbook).dispose();
    }

    private CellStyle getTypedStyle(CellType cellType) {
        return getTypedStyle(cellType, CellColor.COLOR_NONE);
    }

    private CellStyle getTypedStyle(CellType cellType, Color color) {
        if (cellStyleMap.containsKey(cellType)) {
            return cellStyleMap.get(cellType);
        }
        XSSFCellStyle typedStyle = (XSSFCellStyle) workbook.createCellStyle();
        DataFormat dataFormat = workbook.createDataFormat();
        cellType.createTypedStyle(typedStyle, dataFormat, color);
        cellStyleMap.put(cellType, typedStyle);
        return typedStyle;
    }
}