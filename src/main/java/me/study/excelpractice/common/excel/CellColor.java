package me.study.excelpractice.common.excel;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

import java.awt.Color;

public class CellColor {
    private CellColor() {
        throw new IllegalStateException("Utility class");
    }
    public static final Color COLOR_BLUE = new Color(223, 235, 246);
    public static final Color COLOR_NONE = new Color(255, 255, 255);
}