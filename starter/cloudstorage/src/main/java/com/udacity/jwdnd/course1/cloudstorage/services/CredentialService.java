package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class CredentialService {

    private final CredentialMapper credentialMapper;

    public CredentialService(CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
    }

    //ToDelete
    @PostConstruct
    public void postConstruct(){
        System.out.println("CredentialService Bean is created!");
    }

    public List<Credential> getUserCredentials(Integer userId){
        return credentialMapper.getUserCredentials(userId);
    }

    public Credential getCredential(Integer credentialId){
        return credentialMapper.getCredential(credentialId);
    }

    public List<Credential> getAllCredentials(){
        return credentialMapper.getAllCredentials();
    }

    public Integer addCredential(Credential credential){
        return credentialMapper.addCredential(credential);
    }

    public Integer updateCredential(Credential credential){
        return credentialMapper.update(credential);
    }

    public Integer deleteCredential(Integer credentialId){
        return credentialMapper.delete(credentialId);
    }

}
