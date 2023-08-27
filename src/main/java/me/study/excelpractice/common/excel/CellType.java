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
            (cell, val) -> {
                cell.setCellValue(val.toString());
                return cell;
            },
            (cell, style) -> {
                cell.setCellStyle(style);
                return cell;
    }),
    L_TEXT(null,
            (cell, val) -> {
                cell.setCellValue(val.toString());
                return cell;
            },
            (cell, style) -> {
                style.setAlignment(HorizontalAlignment.LEFT);
                cell.setCellStyle(style);
                return cell;
    }),
    NUMBER(null,
            (cell, val) -> {
                cell.setCellValue((Long) val);
                return cell;
            },
            (cell, style) -> {
                cell.setCellStyle(style);
                return cell;
    }),
    DATE("yyyy-MM-dd hh:mm:ss",
            (cell, val) -> {
                cell.setCellValue((LocalDateTime) val);
                return cell;
            },
            (cell, style) -> {
                cell.setCellStyle(style);
                return cell;
    }),
    HEADER(null,
            (cell, val) -> {
                cell.setCellValue(val.toString());
                return cell;
            },
            (cell, style) -> {
                cell.setCellStyle(style);
                return cell;
    });

    private final String format;
    private final BiFunction<Cell, Object, Cell> typedValue;
    private final BiFunction<Cell, CellStyle, Cell> typedStyle;


    protected Cell applyCellValue(Cell cell, Object val) {
        return typedValue.apply(cell, val);
    }

    protected Cell applyCellStyle(Cell cell, CellStyle cellStyle) {
        return typedStyle.apply(cell, cellStyle);
    }
}