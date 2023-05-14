package me.study.excelpractice.todo.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import me.study.excelpractice.todo.domain.enums.TodoStatus;

@Getter
@Entity
@Table(name = "todo")
public class TodoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String todo;
    @Enumerated(EnumType.STRING)
    private TodoStatus status;
    private LocalDateTime dueDate;
    private Integer spendTime;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
