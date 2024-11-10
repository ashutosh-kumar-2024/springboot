package com.example.uploader.controller;
import com.example.uploader.model.FileRecord;
import com.example.uploader.repository.FileRecordRepository;
import com.example.uploader.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class FileUploadController {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private FileRecordRepository fileRecordRepository;

    @GetMapping("/")
    public String index() {
        return "upload";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) {
        try {
            String s3Url = fileStorageService.uploadFile(file);
            FileRecord fileRecord = new FileRecord();
            fileRecord.setFileName(file.getOriginalFilename());
            fileRecord.setS3Url(s3Url);
            fileRecordRepository.save(fileRecord);

            model.addAttribute("message", "File uploaded successfully!");
            model.addAttribute("fileId", fileRecord.getId());
            model.addAttribute("s3Url", s3Url);
        } catch (IOException e) {
            model.addAttribute("message", "File upload failed!");
            e.printStackTrace();
        }
        return "upload";
    }
}