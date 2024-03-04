package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Select("select * from CREDENTIALS where userId = #{userId}")
    List<Credential> getUserCredentials(Integer userId);

    @Select("select * from CREDENTIALS where credentialId = #{credentialId}")
    Credential getCredential(Integer credentialId);

    @Select("select * from CREDENTIALS")
    List<Credential> getAllCredentials();

    @Insert("insert into CREDENTIALS (url, username, key, password, userId) values (#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    Integer addCredential(Credential credential);
    @Update("update CREDENTIALS set url = #{url}, username = #{username}, key = #{key}, password = #{password} where credentialId = #{credentialId}")
    Integer update(Credential credential);
    @Delete("delete from CREDENTIALS where credentialId = #{credentialId}")
    Integer delete(Integer credential);

}
