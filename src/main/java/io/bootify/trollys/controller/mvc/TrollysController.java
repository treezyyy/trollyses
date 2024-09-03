package io.bootify.trollys.controller.mvc;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TrollysController {


    @GetMapping("/")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/equipments/{vin}")
    public String equipment(Model model) {
        return "transport-details";
    }

}
