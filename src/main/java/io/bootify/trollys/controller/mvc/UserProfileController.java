package io.bootify.trollys.controller.mvc;

import io.bootify.trollys.entity.Task;
import io.bootify.trollys.entity.User;
import io.bootify.trollys.repos.UserRepository;
import io.bootify.trollys.service.BonusService;
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
@RequestMapping("/profile")
public class UserProfileController {

    private final BonusService bonusService;
    private final UserRepository userRepository;
    private final TaskService taskService;


    @GetMapping
    public String showProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByName(username)
                .orElseThrow(() -> new IllegalArgumentException("Такой пользователь не найден: " + username));

        List<Task> tasks = taskService.getUserTask(user);

        model.addAttribute("tasks", tasks);
        model.addAttribute("user", user);
        model.addAttribute("bonusPoints", bonusService.getUserBonusPoints(user));
        return "profile";
    }

    @PostMapping("/claim-bonus")
    public String claimBonus(RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByName(username)
                .orElseThrow(() -> new IllegalArgumentException("Такой пользователь не найден:  " + username));

        boolean bonus = bonusService.giveDailyBonus(user);
        if (bonus){
            redirectAttributes.addFlashAttribute("message", "Ежедневный бонус получен успешно!");
        } else {
            redirectAttributes.addFlashAttribute("message", "Вы сегодня уже получали бонус!");

        }
        return "redirect:/profile";
    }

    @PostMapping("/submit-task")
    public String submitTask(@RequestParam Long taskId, RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByName(username)
                .orElseThrow(() -> new IllegalArgumentException("Такой пользователь не найден:  " + username));

        taskService.submitTask(taskId, user);
        redirectAttributes.addFlashAttribute("message", "Задача отправлена на подтверждение.");
        return "redirect:/profile";
    }

}
