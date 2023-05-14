package me.study.excelpractice.common.excel;

import java.awt.Color;
import java.time.LocalDateTime;
import java.util.function.BiFunction;
import java.util.function.Function;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;

@Getter
@RequiredArgsConstructor
public enum CellType {
    TEXT(null,
            cellStyle -> cellStyle,
            (cell, val) -> {
        cell.setCellValue(val.toString());
        return cell;
    }),
    L_TEXT(null,
            cellStyle -> {
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        return cellStyle;
        }, (cell, val) -> {
        cell.setCellValue(val.toString());
        return cell;
    }),
    NUMBER(null,
            cellStyle -> cellStyle,
            (cell, val) -> {
        cell.setCellValue((Long) val);
        return cell;
    }),
    DATE("yyyy-MM-dd hh:mm:ss",
            cellStyle -> cellStyle,
            (cell, val) -> {
        cell.setCellValue((LocalDateTime) val);
        return cell;
    }),
    HEADER(null,
            cellStyle -> cellStyle,
            (cell, val) -> {
        cell.setCellValue(val.toString());
        return cell;
    });

    private final String format;
    private final Function<CellStyle, CellStyle> additionalStyle;
    private final BiFunction<Cell, Object, Cell> typedValue;

    protected CellStyle applyAdditionalStyle(CellStyle cellStyle) {
        return additionalStyle.apply(cellStyle);
    }
    protected Cell applyCellValue(Cell cell, Object val) {
        return typedValue.apply(cell, val);
    }

    protected final void createTypedStyle(XSSFCellStyle typedStyle, DataFormat dataFormat, Color color) {

        setBaseStyle(typedStyle, color);
        String format = this.getFormat();
        if (format != null) {
            typedStyle.setDataFormat(dataFormat.getFormat(format));
        }
        this.applyAdditionalStyle(typedStyle);
    }

    protected static final void setBaseStyle(XSSFCellStyle cellStyle, Color color) {

        // 배경색 지정
        cellStyle.setFillForegroundColor(new XSSFColor(color, new DefaultIndexedColorMap()));
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // 정렬
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // 테두리
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
    }
}