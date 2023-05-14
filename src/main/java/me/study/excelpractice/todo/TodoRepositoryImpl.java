package me.study.excelpractice.todo;

import static me.study.excelpractice.todo.domain.entity.QTodoEntity.todoEntity;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import me.study.excelpractice.todo.domain.dto.TodoConditionDto;
import me.study.excelpractice.todo.domain.entity.TodoEntity;
import me.study.excelpractice.todo.domain.enums.TodoStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class TodoRepositoryImpl implements TodoRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    @Override
    public Page<TodoEntity> searchTodoListByCondition(TodoConditionDto dto, Pageable pageable) {

        BooleanExpression whereClause = Expressions.allOf(
                betweenCreatedAt(dto.getCreatedStartAt(), dto.getCreatedEndAt()),
                betweenDueDate(dto.getDueStartDate(), dto.getDueEndDate()),
                inStatus(dto.getStatus())
        );

        List<TodoEntity> resultList = queryFactory
                .selectFrom(todoEntity)
                .where(whereClause)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
               // .orderBy(getSortedColumn(pageable.getSort(), todoEntity))
                .fetch();

        Long searchCount = queryFactory
                .select(todoEntity.count())
                .from(todoEntity)
                .where(whereClause)
                .fetchOne();

        return new PageImpl<>(resultList, pageable, searchCount);
    }

    private BooleanExpression betweenCreatedAt(LocalDate createdStartAt, LocalDate createdEndAt) {
        LocalDateTime startAt = null;
        LocalDateTime endAt = null;

        if (createdStartAt != null) {
            startAt = createdStartAt.atTime(0, 0, 0);
        }
        if (createdEndAt != null) {
            endAt = createdEndAt.plusDays(1).atTime(0, 0, 0);
        }
        if (startAt == null && endAt == null) {
            return null;
        }
        return todoEntity.createdAt.between(startAt, endAt);
    }
    private BooleanExpression betweenDueDate(LocalDate dueStartDate, LocalDate dueEndDate) {
        LocalDateTime startDate = null;
        LocalDateTime endDate = null;

        if (dueStartDate != null) {
            startDate = dueStartDate.atTime(0, 0, 0);
        }
        if (dueEndDate != null) {
            endDate = dueEndDate.plusDays(1).atTime(0, 0, 0);
        }
        if (startDate == null && endDate == null) {
            return null;
        }
        return todoEntity.dueDate.between(startDate, endDate);
    }
    private BooleanExpression inStatus(Set<TodoStatus> status) {
        return status == null || status.isEmpty() ? null : todoEntity.status.in(status);
    }
}
