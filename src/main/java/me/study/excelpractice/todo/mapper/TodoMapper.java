package me.study.excelpractice.todo.mapper;

import me.study.excelpractice.todo.domain.dto.TodoExcelDto;
import me.study.excelpractice.todo.domain.entity.TodoEntity;
import org.springframework.stereotype.Component;

@Component
public class TodoMapper {

    public TodoExcelDto toExcelResponseDto(TodoEntity todoEntity) {
        return TodoExcelDto.builder()
                .todoNo(todoEntity.getId())
                .createdAt(todoEntity.getCreatedAt())
                .updatedAt(todoEntity.getModifiedAt())
                .status(todoEntity.getStatus().getDesc())
                .todo(todoEntity.getTodo())
                .spendTime(todoEntity.getSpendTime())
                .dueDate(todoEntity.getDueDate())
                .build();
    }
}
