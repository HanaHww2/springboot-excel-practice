package me.study.excelpractice.common.excel;

import jakarta.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExcelUtil {
    private static final DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private ExcelUtil() {
        throw new IllegalStateException("Utility class");
    }
    public static void setFileName(HttpServletResponse response, String originalFileName) throws UnsupportedEncodingException {

        LocalDateTime today = LocalDateTime.now();
        String fileName = originalFileName + "_" + today.format(dtFormatter) + ".xlsx";
        fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
    }

    public static void setContentType(HttpServletResponse response) {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }
}
