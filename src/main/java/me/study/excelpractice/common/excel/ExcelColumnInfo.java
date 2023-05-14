package me.study.excelpractice.common.excel;

public interface ExcelColumnInfo {
    String getHeaderName();
    String getAttributeName();
    int getOrder();
    int getWidth();
    CellType getCellType();
}