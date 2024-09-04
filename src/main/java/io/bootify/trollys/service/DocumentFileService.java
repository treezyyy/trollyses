package io.bootify.trollys.service;

import io.bootify.trollys.entity.DocumentFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface DocumentFileService {

    DocumentFile uploadFile(MultipartFile file);

    Optional<DocumentFile> getFile(Long fileId);

    List<DocumentFile> getAllFilesByUser(Long userId);

    void deleteFile(Long fileId);

    List<DocumentFile> getAllFiles();

}
