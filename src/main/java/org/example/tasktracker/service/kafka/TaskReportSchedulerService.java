package org.example.tasktracker.service.kafka;

import lombok.RequiredArgsConstructor;
import org.example.tasktracker.model.Task;
import org.example.tasktracker.model.User;
import org.example.tasktracker.model.kafka.EmailTask;
import org.example.tasktracker.service.TaskService;
import org.example.tasktracker.service.UserService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskReportSchedulerService {

    private final UserService userService;

    private final KafkaTemplate<String, EmailTask> kafkaTemplate;

    private static final String TASK_REPORT_TOPIC = "EMAIL-SENDING-TASKS";

    @Scheduled(cron = "0 0 0 * * ?")
    public void sendDailyTaskReports() {

        List<User> allUsers = userService.findAllUsers();

        LocalDateTime today = LocalDateTime.now();
        LocalDateTime yesterday = today.minusDays(1);

        for (User user : allUsers) {
            List<Task> finishedTasks = userService.getFinishedTasks(user.getId(), today, yesterday);
            List<Task> unfinishedTasks = userService.getUnfinishedTasks(user.getId(), today, yesterday);

            StringBuilder emailBody = new StringBuilder();

            if (!unfinishedTasks.isEmpty()) {
                int count = unfinishedTasks.size();
                emailBody.append("У вас осталось ").append(count).append("несделанных задач");

                unfinishedTasks.forEach(task -> {
                    emailBody.append("- ").append(task.getTitle()).append("\n");
                });
            }

            if (!finishedTasks.isEmpty()) {

                int count = finishedTasks.size();
                emailBody.append("За сегодня вы выполнили ").append(count).append(" задач:\n");

                finishedTasks.forEach(task -> {
                    emailBody.append("- ").append(task.getTitle()).append("\n");
                });
            }

            EmailTask emailTask = EmailTask.builder()
                    .recipient(user.getEmail())
                    .subject("Ваш ежедневный отчет по задачам")
                    .body(emailBody.toString())
                    .build();

            kafkaTemplate.send(TASK_REPORT_TOPIC, emailTask);
        }


    }
}
