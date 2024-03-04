package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    private UserService userService;
    private CredentialService credentialService;
    private FileService fileService;
    private NoteService noteService;
    private EncryptionService encryptionService;

    public HomeController(UserService userService, CredentialService credentialService, FileService fileService, NoteService noteService, EncryptionService encryptionService) {
        this.userService = userService;
        this.credentialService = credentialService;
        this.fileService = fileService;
        this.noteService = noteService;
        this.encryptionService = encryptionService;
    }

    @GetMapping
    public String getHomePage(Authentication authentication, Credential credential,
                              Note note, Model model){

        String username = authentication.getName();
        model.addAttribute("notes", this.noteService.getUserNotes(this.userService.getUserId(username)));
        model.addAttribute("credentials", this.credentialService.getUserCredentials(this.userService.getUserId(username)));
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("files", this.fileService.getUserFiles(this.userService.getUserId(username)));
        return "home";
    }
}
