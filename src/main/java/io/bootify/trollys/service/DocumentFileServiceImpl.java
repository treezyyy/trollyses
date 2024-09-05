package io.bootify.trollys.service;

import io.bootify.trollys.entity.DocumentFile;
import io.bootify.trollys.entity.User;
import io.bootify.trollys.repos.DocumentFileRepository;
import io.bootify.trollys.repos.UserRepository;
import lombok.AllArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DocumentFileServiceImpl implements DocumentFileService{

    private final UserRepository userRepository;
    private final DocumentFileRepository documentFileRepository;

    @Override
    public void uploadFile(MultipartFile file){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = (userRepository.findByName(username)
                .orElseThrow(() -> new IllegalArgumentException("User не найлен c username" + username)));
        DocumentFile documentFile = new DocumentFile();
        documentFile.setFileType(file.getContentType());
        documentFile.setFileName(file.getOriginalFilename());
        documentFile.setUploadDate(LocalDateTime.now());
        documentFile.setUser(user);
        try {
            documentFile.setData(file.getBytes());
            String uploadDir = "uploads/";
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Path filePath = uploadPath.resolve(file.getOriginalFilename());
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            documentFile.setFilePath(filePath.toString());

        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to store file data", e);
        }
        documentFileRepository.save(documentFile);
    }

}
