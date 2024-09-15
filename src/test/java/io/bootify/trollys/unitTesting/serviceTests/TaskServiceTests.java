package io.bootify.trollys.unitTesting.serviceTests;

import io.bootify.trollys.entity.Task;
import io.bootify.trollys.entity.TaskHistory;
import io.bootify.trollys.entity.TaskStatus;
import io.bootify.trollys.entity.User;
import io.bootify.trollys.repos.TaskHistoryRepository;
import io.bootify.trollys.repos.TaskRepository;
import io.bootify.trollys.repos.UserRepository;
import io.bootify.trollys.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class TaskServiceTests {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskHistoryRepository taskHistoryRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskService taskService;

    private User user;
    private Task task;
    private TaskHistory taskHistory;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setBonusPoints(0);
        task = new Task();
        task.setId(1L);
        task.setStatus(TaskStatus.AVAILABLE);
        task.setPoints(10);
        task.setUser(user);
        taskHistory = new TaskHistory();
        taskHistory.setTask(task);
    }

    @Test
    void takeTaskTest() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        taskService.takeTask(1L, user);

        assertEquals(TaskStatus.TAKEN, task.getStatus());
        assertEquals(user, task.getUser());

        verify(taskHistoryRepository, times(1)).save(any(TaskHistory.class));
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void rejectTaskTest() {
        task.setStatus(TaskStatus.PENDING);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        taskService.rejectTask(1L);

        assertEquals(TaskStatus.REJECTED, task.getStatus());

        verify(taskRepository, times(1)).save(task);
    }
    @Test
    void confirmTaskTest() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskHistoryRepository.findByTaskId(1L)).thenReturn(Optional.of(taskHistory));

        taskService.confirmTask(1L);

        assertEquals(TaskStatus.AVAILABLE, task.getStatus());
        assertEquals(10, user.getBonusPoints());

        assertEquals(LocalDateTime.now().getDayOfYear(), taskHistory.getDataCompleted().getDayOfYear());

        verify(userRepository, times(1)).save(user);
        verify(taskRepository, times(1)).save(task);
        verify(taskHistoryRepository, times(1)).save(taskHistory);
    }
}
