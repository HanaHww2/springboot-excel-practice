package me.study.excelpractice.todo.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TodoStatus {
    TODO("투두"),
    DONE("완료"),
    DOING("진행중"),
    DELAYED("지연");

    private final String desc;
}
