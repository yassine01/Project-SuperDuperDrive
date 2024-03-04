package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class FileService {

    private FileMapper fileMapper;
    private UserMapper userMapper;

    public FileService(FileMapper fileMapper, UserMapper userMapper) {
        this.fileMapper = fileMapper;
        this.userMapper = userMapper;
    }

    @PostConstruct
    public void postConstruct(){
        System.out.println("FileService bean is created.");
    }

    File getFile(int fileId){
        return fileMapper.getFile(fileId);
    }

    public File getUserFile(Integer userId, String fileName){
        return fileMapper.getUserFile(userId, fileName);
    }

    public List<File> getUserFiles(Integer userId){
        return fileMapper.getUserFiles(userId);
    }

    public Integer addFile(MultipartFile uploadFile, String username) throws Exception {
        Integer fileId = null;
        String fileName = uploadFile.getOriginalFilename();
        Integer userId = userMapper.getUser(username).getUserId();
        String contentType = uploadFile.getContentType();
        String fileSize = String.valueOf(uploadFile.getSize());
        byte[] fileData = uploadFile.getBytes();
        return fileMapper.addFile(
                new File(fileId, fileName, contentType, fileSize, userId, fileData));
    }

    public Integer deleteFile(int fileId){
        return fileMapper.delete(fileId);
    }
}
