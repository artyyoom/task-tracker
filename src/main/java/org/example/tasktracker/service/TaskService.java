package org.example.tasktracker.service;

import lombok.RequiredArgsConstructor;
import org.example.tasktracker.exception.DataNotFoundException;
import org.example.tasktracker.model.Task;
import org.example.tasktracker.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public List<Task> getTasksByUserId(Long userId) {

        return taskRepository.findTasksByUserId(userId);
    }

    public Task addTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(Task task) {

        Task taskById = taskRepository.findById(task.getId())
                .orElseThrow(() -> new DataNotFoundException("Task not found"));
        taskById.setTitle(task.getTitle());
        taskById.setDescription(task.getDescription());
        taskById.setStatus(task.getStatus());
        taskById.setDone_timestamp(task.getDone_timestamp());

        return taskRepository.save(taskById);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
