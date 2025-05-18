package org.example.tasktracker.service;

import lombok.RequiredArgsConstructor;
import org.example.tasktracker.model.Task;
import org.example.tasktracker.model.User;
import org.example.tasktracker.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public List<Task> getTasksByUserId(User user) {

        return taskRepository.findTasksByUserId(user);
    }

    public Task addTask(Task task) {
        return taskRepository.save(task);
    }
}
