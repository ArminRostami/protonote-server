package com.example.proto.controllers;

import java.util.List;
import javax.validation.Valid;
import com.example.proto.models.Note;
import com.example.proto.models.UserNotes;
import com.example.proto.services.NoteService;

import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notes")
public class NoteController {
    private NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping("")
    public void createNote(@Valid @RequestBody Note note) {
        this.noteService.createNote(note);

    }

    @GetMapping("")
    public List<UserNotes> readNotes() {
        return this.noteService.readNotes();
    }

    @PutMapping("/{id}")
    public void updateNote(@Valid @RequestBody Note note, @PathVariable("id") ObjectId id) {
        this.noteService.updateNote(id, note);

    }

    @DeleteMapping("/{id}")
    public void deleteNote(@PathVariable("id") ObjectId id) {
        this.noteService.deleteNote(id);
    }

    @PostMapping("/share/{id}")
    public void shareNote(@RequestBody String targetUser, @PathVariable("id") ObjectId id) {
        this.noteService.shareNote(targetUser, id);
    }

}