package me.study.excelpractice.todo.domain.dto;

import java.time.LocalDate;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.study.excelpractice.todo.domain.enums.TodoStatus;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class TodoConditionDto {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdStartAt;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdEndAt;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueEndDate;
    private Set<TodoStatus> status;
}
