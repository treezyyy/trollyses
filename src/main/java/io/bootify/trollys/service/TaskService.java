package io.bootify.trollys.service;

import io.bootify.trollys.entity.Task;
import io.bootify.trollys.entity.TaskHistory;
import io.bootify.trollys.entity.TaskStatus;
import io.bootify.trollys.entity.User;
import io.bootify.trollys.repos.TaskHistoryRepository;
import io.bootify.trollys.repos.TaskRepository;
import io.bootify.trollys.repos.UserRepository;
import lombok.AllArgsConstructor;
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

    public List<Task> getAvailableTasks() {
        return taskRepository.findByStatus(TaskStatus.AVAILABLE);
    }

    @Transactional
    public void takeTask(Long taskId, User user) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Задача не найдена"));
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
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Задача не найдена: " + taskId));
        task.setStatus(TaskStatus.PENDING);
        taskRepository.save(task);
    }

    @Transactional
    public void confirmTask(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Задача не найдена"));

        if (task.getStatus() == TaskStatus.PENDING) {
            task.setStatus(TaskStatus.COMPLETED);
            User user = task.getUser();
            user.setBonusPoints(user.getBonusPoints() + task.getPoints());
            TaskHistory taskHistory = taskHistoryRepository.findByTaskId(taskId)
                    .orElseThrow(() -> new IllegalArgumentException("История задачи с ID " + taskId + " не найдена."));
            taskHistory.setDataCompleted(LocalDateTime.now());
            taskHistoryRepository.save(taskHistory);
            userRepository.save(user);
            taskRepository.save(task);
        }
    }

    public void rejectTask(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Задача не найдена"));

        if (task.getStatus() == TaskStatus.PENDING) {
            task.setStatus(TaskStatus.REJECTED);
            taskRepository.save(task);
        }
    }

    public List<Task> getPendingTasks(){
        return taskRepository.getPendingTasks();
    }

}
