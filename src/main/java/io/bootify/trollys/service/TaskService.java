package io.bootify.trollys.service;

import io.bootify.trollys.entity.Task;
import io.bootify.trollys.entity.TaskHistory;
import io.bootify.trollys.entity.TaskStatus;
import io.bootify.trollys.entity.User;
import io.bootify.trollys.repos.TaskHistoryRepository;
import io.bootify.trollys.repos.TaskRepository;
import io.bootify.trollys.repos.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskHistoryRepository taskHistoryRepository;
    private final UserRepository userRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);


    public List<Task> getAvailableTasks() {
        return taskRepository.findByStatus(TaskStatus.AVAILABLE);
    }

    @Transactional
    public void takeTask(Long taskId, User user) {
        LOGGER.debug("Старт взятия задачи");
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> {
                    LOGGER.error("Задачи нет в базе");
                    return new IllegalArgumentException("Задача не найдена в базе с id " + taskId);
                });
        if (task.getStatus() == TaskStatus.AVAILABLE) {
            task.setUser(user);
            task.setStatus(TaskStatus.TAKEN);
            TaskHistory taskHistory = new TaskHistory();
            taskHistory.setTask(task);
            taskHistory.setUser(user);
            taskHistory.setDateTaken(LocalDateTime.now());
            taskHistory.setPointsEarned(task.getPoints());
            taskRepository.save(task);
            taskHistoryRepository.save(taskHistory);
        }
    }

    public List<Task> getUserTask(User user){
        return taskRepository.getUserTask(user);
    }


    public void submitTask(Long taskId, User user) {
        LOGGER.debug("Старт измения статуса задачи");
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> {
                    LOGGER.error("Задачи нет в базе при одобрении");
                    return new IllegalArgumentException("Задача не найдена в базе с id " + taskId);
                });
        task.setStatus(TaskStatus.PENDING);
        taskRepository.save(task);
    }

    @Transactional
    public void confirmTask(Long taskId) {
        LOGGER.debug("Старт одобрения задачи");
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> {
                    LOGGER.error("Задачи нет в базе при отклонении");
                    return new IllegalArgumentException("Задача не найдена в базе с id " + taskId);
                });
        if (task.getStatus() == TaskStatus.PENDING) {
            task.setStatus(TaskStatus.COMPLETED);
            User user = task.getUser();
            user.setBonusPoints(user.getBonusPoints() + task.getPoints());
            TaskHistory taskHistory = taskHistoryRepository.findByTaskId(taskId)
                    .orElseThrow(() -> {
                        LOGGER.error("Нет истории задачи с id: {}", taskId);
                        return new IllegalArgumentException("История задачи с ID " + taskId + " не найдена.");
                    });

            taskHistory.setDataCompleted(LocalDateTime.now());
            taskHistoryRepository.save(taskHistory);
            userRepository.save(user);
            taskRepository.save(task);
        }
    }

    public void rejectTask(Long taskId) {
        LOGGER.debug("Старт отклонения задачи");
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> {
                    LOGGER.error("Нет задачи с id: {}", taskId);
                    return new IllegalArgumentException("История задачи с ID " + taskId + " не найдена.");
                });

        if (task.getStatus() == TaskStatus.PENDING) {
            task.setStatus(TaskStatus.REJECTED);
            taskRepository.save(task);
        }
    }

    public List<Task> getPendingTasks(){
        return taskRepository.getPendingTasks();
    }

}
