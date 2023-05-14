package me.study.excelpractice.todo;

import me.study.excelpractice.todo.domain.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, Long>, TodoRepositoryCustom {

}
