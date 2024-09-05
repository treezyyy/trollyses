package io.bootify.trollys.controller.mvc;

import io.bootify.trollys.service.DocumentFileServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
@RequestMapping("/documents")
public class DocumentFileController {

    private final DocumentFileServiceImpl documentFileService;

    @GetMapping("/upload")
    public String showUploadForm(Model model) {
        return "uploadForm";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file,
                             RedirectAttributes redirectAttributes) {
        documentFileService.uploadFile(file);
        redirectAttributes.addFlashAttribute("message", "File uploaded successfully!");
        return "redirect:/";
    }

}
