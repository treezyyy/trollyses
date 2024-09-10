package io.bootify.trollys.controller.mvc;


import io.bootify.trollys.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final TaskService taskService;

    @GetMapping()
    public String index(Model model) {
        return "adminIndex";
    }

    @PostMapping("/admin/confirm-task")
    public String confirmTask(@RequestParam Long taskId, Model model) {
        taskService.confirmTask(taskId);
        model.addAttribute("message", "Задача подтверждена.");
        return "redirect:/admin/tasks";
    }

    @PostMapping("/admin/reject-task")
    public String rejectTask(@RequestParam Long taskId, Model model) {
        taskService.rejectTask(taskId);
        model.addAttribute("message", "Задача отклонена.");
        return "redirect:/admin/tasks";
    }


}
