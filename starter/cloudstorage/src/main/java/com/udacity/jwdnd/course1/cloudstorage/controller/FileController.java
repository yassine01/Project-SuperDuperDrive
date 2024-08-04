package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    private final FileService fileService;
    private final UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("fileUpload") MultipartFile multipartFile,
                         Authentication authentication, Model model) throws IOException {
        Integer userId = userService.getUserId(authentication.getName());

        if (multipartFile.isEmpty()) {
            model.addAttribute("error", "Please select a valid file !");
            logger.warn("File upload failed: No file selected");
            return "result";
        }
        if (fileService.getUserFile(userId, multipartFile.getOriginalFilename()) != null) {
            model.addAttribute("error", "existing file ! Try with different file!");
            logger.warn("File upload failed: File already exists");
            return "result";
        }

        try {
           // Integer res = fileService.addFile(multipartFile, authentication.getName());
            logger.info("File uploaded successfully: {}", multipartFile.getOriginalFilename());
        } catch (Exception e) {
            logger.error("File upload failed: {}", e.getMessage(), e);
            throw new IOException(e.getMessage());
        }
        model.addAttribute("success", "File uploaded successfully!");
        model.addAttribute("message", "File uploaded successfully!");
        return "result";
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> download(@RequestParam String fileName, Authentication authentication) {
        Integer userId = userService.getUserId(authentication.getName());

        File file = fileService.getUserFile(userId, fileName);
        if (file == null) {
            logger.warn("File download failed: File not found");
            return ResponseEntity.notFound().build();
        }
        byte[] fileData = file.getFileData();

        ByteArrayResource resource = new ByteArrayResource(fileData);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"");
        headers.add(HttpHeaders.CONTENT_TYPE, file.getContentType());

        logger.info("File downloaded successfully: {}", fileName);
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

    @GetMapping("/deleteFile")
    public String delete(@RequestParam Integer fileId, Model model) {
        Integer rowsDeleted = fileService.deleteFile(fileId);
        if (rowsDeleted > 0) {
            model.addAttribute("success", "File deleted!");
            model.addAttribute("message", "File deleted!");
            logger.info("File deleted successfully: {}", fileId);
        } else {
            model.addAttribute("error", "There was an error during the suppression ! Please, try again..");
            logger.warn("File deletion failed: File ID {}", fileId);
        }
        return "redirect:/home";
    }
}