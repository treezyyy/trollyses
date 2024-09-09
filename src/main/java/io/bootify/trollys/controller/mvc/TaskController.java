package io.bootify.trollys.controller.mvc;

import io.bootify.trollys.entity.Task;
import io.bootify.trollys.entity.User;
import io.bootify.trollys.repos.UserRepository;
import io.bootify.trollys.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final UserRepository userRepository;

    @GetMapping("/available")
    public String showAvailableTasks(Model model) {
        List<Task> availableTasks = taskService.getAvailableTasks();
        model.addAttribute("tasks", availableTasks);
        return "availableTasks";
    }

    @PostMapping("/take")
    public String takeTask(@RequestParam Long taskId, RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByName(username)
                .orElseThrow(() -> new IllegalArgumentException("Такой пользователь не найден: " + username));

        taskService.takeTask(taskId, user);
        redirectAttributes.addFlashAttribute("message", "Задача взята успешно!");

        return "redirect:/tasks/available";
    }

}