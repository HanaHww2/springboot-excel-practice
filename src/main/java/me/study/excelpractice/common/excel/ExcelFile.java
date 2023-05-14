package me.study.excelpractice.common.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public interface ExcelFile<T> {
    <E extends Enum<E> & ExcelColumnInfo> void setMetaInfo(Class<E> excelInfoClazz);
    void renderHeader();
    void renderBody(List<T> dtoList);
    void write(OutputStream stream) throws IOException;
}