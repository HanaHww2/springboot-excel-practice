package me.study.excelpractice.common.excel;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;

import java.awt.Color;
import java.util.EnumMap;
import java.util.Map;

public class CellStyleStore {
    private final Map<CellType, CellStyle> cellStyleMap = new EnumMap<>(CellType.class);
    private Workbook workbook;

    public CellStyleStore(Workbook workbook) {
        this.workbook = workbook;
    }

    public CellStyle getTypedStyle(CellType cellType) {
        return this.getTypedStyle(cellType, CellColor.COLOR_NONE);
    }

    public CellStyle getTypedStyle(CellType cellType, Color color) {
        if (cellStyleMap.containsKey(cellType)) {
            return cellStyleMap.get(cellType);
        }
        this.createTypedStyle(workbook, cellType, color);
        return cellStyleMap.get(cellType);
    }

    protected final void createTypedStyle(Workbook workbook, CellType cellType, Color color) {

        XSSFCellStyle typedStyle = (XSSFCellStyle) workbook.createCellStyle();
        this.setBaseStyle(typedStyle, color);

        String format = cellType.getFormat();
        if (format != null) {
            DataFormat dataFormat = workbook.createDataFormat();
            typedStyle.setDataFormat(dataFormat.getFormat(format));
        }
        cellStyleMap.put(cellType, typedStyle);
    }

    protected static final void setBaseStyle(XSSFCellStyle cellStyle, Color color) {

        // 테두리
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);

        // 배경색 지정
        cellStyle.setFillForegroundColor(new XSSFColor(color, new DefaultIndexedColorMap()));
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // 정렬
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
    }
}