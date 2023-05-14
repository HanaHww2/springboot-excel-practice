package me.study.excelpractice.todo;

import me.study.excelpractice.todo.domain.dto.TodoConditionDto;
import me.study.excelpractice.todo.domain.entity.TodoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TodoRepositoryCustom {
    Page<TodoEntity> searchTodoListByCondition(TodoConditionDto dto, Pageable pageable);
}
