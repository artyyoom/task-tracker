package org.example.tasktracker.repository;

import org.example.tasktracker.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findTasksByUserId(Long userId);

    List<Task> getTasksById(Long id);

    Task findTaskById(Long id);
}
