package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("select * from FILES where fileId = #{fileId}")
    File getFile(int fileId);

    @Select("select * from FILES where userId = #{userId} and fileName = #{fileName}")
    File getUserFile(int userId, String fileName);

    @Select("select * from FILES where userId = #{userId}")
    List<File> getUserFiles(Integer userId);

    @Select("select  * from FILES")
    List<File> getAllFiles();

    @Insert("insert into FILES (fileName, userId, contentType, fileSize, fileData) values (#{fileName}, #{userId}, #{contentType}, #{fileSize}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer addFile(File file);
    @Delete("delete from FILES where fileId = #{fileId}")
    Integer delete(Integer fileId);
}
