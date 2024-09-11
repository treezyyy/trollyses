package io.bootify.trollys.controller.mvc;


import io.bootify.trollys.entity.Task;
import io.bootify.trollys.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final TaskService taskService;

    @GetMapping()
    public String index(Model model) {
        List<Task> pendingTasks = taskService.getPendingTasks();
        model.addAttribute("tasks", pendingTasks);
        System.out.println("Pending" + pendingTasks.size());
        model.addAttribute("message", "Задачи успешно загружены");
        return "adminIndex";
    }

    @PostMapping("/confirm-task")
    public String confirmTask(@RequestParam Long taskId, Model model) {
        taskService.confirmTask(taskId);
        model.addAttribute("message", "Задача подтверждена.");
        return "redirect:/admin";
    }

    @PostMapping("/reject-task")
    public String rejectTask(@RequestParam Long taskId, Model model) {
        taskService.rejectTask(taskId);
        model.addAttribute("message", "Задача отклонена.");
        return "redirect:/admin";
    }


}
