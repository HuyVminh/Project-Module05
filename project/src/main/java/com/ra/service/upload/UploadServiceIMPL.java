package com.ra.service.upload;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class UploadServiceIMPL implements UploadService{
    @Value("${path-upload}")
    private String path;
    @Value("${server.port}")
    private String serverPort;

    @Override
    public String uploadImage(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        try {
            FileCopyUtils.copy(file.getBytes(), new File(path + fileName));
            return "http://localhost:" + serverPort + "/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
