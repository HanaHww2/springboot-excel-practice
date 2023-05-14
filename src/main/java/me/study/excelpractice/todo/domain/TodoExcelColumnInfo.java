package me.study.excelpractice.todo.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.study.excelpractice.common.excel.CellType;
import me.study.excelpractice.common.excel.ExcelColumnInfo;

@Getter
@RequiredArgsConstructor
public enum TodoExcelColumnInfo implements ExcelColumnInfo {

    CONSULTATION_NO("번호", "todoNo", CellType.NUMBER, 0, 3000),
    TODO("할 일", "todo", CellType.TEXT, 1, 5000),
    STATUS("상태", "status", CellType.TEXT, 2, 3000),
    CREATED_AT("등록일시", "createdAt", CellType.DATE ,3, 5000),
    UPDATED_AT("수정일시", "updatedAt", CellType.DATE,4, 5000),
    DUE_DATE("기한", "dueDate", CellType.DATE, 5, 5000),
    USED_SOLUTION("소요 시간", "spendTime", CellType.NUMBER, 6, 4000);

    private final String headerName;
    private final String attributeName;
    private final CellType cellType;
    private final int order;
    private final int width;
}