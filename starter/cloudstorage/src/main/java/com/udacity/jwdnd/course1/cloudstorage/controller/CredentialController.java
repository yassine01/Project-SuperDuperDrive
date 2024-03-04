package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
public class CredentialController {

    private CredentialService credentialService;
    private EncryptionService encryptionService;
    private UserService userService;

    public CredentialController(CredentialService credentialService, EncryptionService encryptionService, UserService userService) {
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
        this.userService = userService;
    }

    @PostMapping("/updateCredential")
    public String addOrUpdateCredential(@ModelAttribute("credential")Credential credential,
                                        Authentication authentication, Model model,
                                        EncryptionService encryptionService){
        Integer userId = userService.getUserId(authentication.getName());
        credential.setUserId(userId);
        credential.setKey(userService.getUser(authentication.getName()).getSalt());
        credential.setPassword(encryptionService.encryptValue(credential.getPassword(), userService.getUser(authentication.getName()).getSalt()));

        if(credential.getCredentialId() == null) {
            credentialService.addCredential(credential);
        } else {
            credentialService.updateCredential(credential);
        }
        model.addAttribute("credentials", credentialService.getUserCredentials(userId));
        model.addAttribute("success", true);
        return "result";
    }

    @GetMapping("/deleteCredential")
    public String deleteCredential(@RequestParam("credentialId") Integer credentialId,
                                   Authentication authentication, Model model){
        String username = authentication.getName();
        Integer userId = userService.getUserId(username);

        credentialService.deleteCredential(credentialId);

        model.addAttribute("credentials", credentialService.getUserCredentials(userId));
        model.addAttribute("success", true);
        return "result";
    }
}
