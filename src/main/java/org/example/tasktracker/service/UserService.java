package org.example.tasktracker.service;

import lombok.RequiredArgsConstructor;
import org.example.tasktracker.model.Task;
import org.example.tasktracker.model.User;
import org.example.tasktracker.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public List<Task> getFinishedTasks(Long userId, LocalDateTime startOfDay, LocalDateTime endOfDay) {
        return userRepository.findTasksByUserIdAndStatus(userId, "finished", startOfDay, endOfDay);
    }

    public List<Task> getUnfinishedTasks(Long userId, LocalDateTime startOfDay, LocalDateTime endOfDay) {
        return userRepository.findTasksByUserIdAndStatus(userId, "unfinished", startOfDay, endOfDay);
    }
}
