package me.study.excelpractice.todo.api;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import me.study.excelpractice.common.dto.DefaultPageDto;
import me.study.excelpractice.common.excel.CellColor;
import me.study.excelpractice.common.excel.ExcelUtil;
import me.study.excelpractice.common.excel.SimpleExcelFile;
import me.study.excelpractice.todo.service.TodoService;
import me.study.excelpractice.todo.domain.TodoExcelColumnInfo;
import me.study.excelpractice.todo.domain.dto.TodoConditionDto;
import me.study.excelpractice.todo.domain.dto.TodoExcelDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TodoController {
    private final TodoService todoService;

    @GetMapping( "/todo/excel")
    public void getListExcel(
            TodoConditionDto conditions,
            HttpServletResponse response) throws IOException {

        ExcelUtil.setFileName(response, "투두 리스트");
        ExcelUtil.setContentType(response);

        SimpleExcelFile<TodoExcelColumnInfo, TodoExcelDto> excelFile = new SimpleExcelFile<>(CellColor.COLOR_BLUE);
        excelFile.setMetaInfo(TodoExcelColumnInfo.class);
        excelFile.renderHeader();

        int total = -1;
        int page = 0;

        DefaultPageDto<TodoExcelDto> pageDto;
        while(total >= page || total == -1) {
            var pageable = PageRequest.of(page++, 1000);
            pageDto = todoService.searchTodoForExcel(conditions, pageable);
            if (total == -1) {
                total = pageDto.getTotalPages();
            }
            excelFile.renderBody(pageDto.getResult());
        }
        excelFile.write(response.getOutputStream());
    }
}
