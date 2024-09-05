package io.bootify.trollys.service;

import org.springframework.web.multipart.MultipartFile;

public interface DocumentFileService {

    void uploadFile(MultipartFile file);


}
