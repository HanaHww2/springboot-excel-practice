package me.study.excelpractice.todo.service;

import lombok.RequiredArgsConstructor;
import me.study.excelpractice.common.dto.DefaultPageDto;
import me.study.excelpractice.todo.mapper.TodoMapper;
import me.study.excelpractice.todo.domain.dto.TodoConditionDto;
import me.study.excelpractice.todo.domain.dto.TodoExcelDto;
import me.study.excelpractice.todo.domain.entity.TodoEntity;
import me.study.excelpractice.todo.repository.TodoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TodoService {
    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;
    @Transactional(readOnly = true)
    public DefaultPageDto<TodoExcelDto> searchTodoForExcel(TodoConditionDto conditions, Pageable pageable) {

        Page<TodoEntity> todoEntities = todoRepository.searchTodoListByCondition(conditions,
                pageable);
        long totalCount = todoRepository.count();
        return new DefaultPageDto<>(todoEntities.map(todoMapper::toExcelResponseDto), totalCount);
    }
}
