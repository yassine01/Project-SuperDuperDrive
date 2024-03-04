package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
public class NoteController {

    private NoteService noteService;
    private UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping("/updateNote")
    public String addOrUpdateNote(@ModelAttribute("note")Note note, Authentication authentication, Model model){
        Integer userId = userService.getUserId(authentication.getName());
        note.setUserId(userId);

        if(note.getNoteId() == null){
            noteService.addNote(note);
        } else {
            noteService.updateNote(note);
        }
        model.addAttribute("success", true);
        model.addAttribute("notes", noteService.getUserNotes(userId));

        return "result";
    }

    @GetMapping("/deleteNote")
    public String deleteNote(@RequestParam("noteId") Integer noteId,
                             Authentication authentication, Model model) {
        String username = authentication.getName();
        Integer userId = userService.getUserId(username);

        noteService.deleteNote(noteId);

        model.addAttribute("notes", this.noteService.getUserNotes(userId));
        model.addAttribute(("success"), true);
        return "result";
    }
}
