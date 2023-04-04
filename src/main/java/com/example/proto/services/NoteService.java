package com.example.proto.services;

import java.io.IOException;
import java.util.List;

import com.example.proto.models.Note;
import com.example.proto.models.UserNotes;
import com.example.proto.repositories.NoteRepository;
import com.example.proto.repositories.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class NoteService {
    private static final Logger logger = LoggerFactory.getLogger(NoteService.class);
    private NoteRepository noteRepository;
    private UserRepository userRepository;

    public NoteService(NoteRepository noteRepository, UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    // saves the note in notes collection then adds it to creator's notes
    public void createNote(Note note) {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        ObjectId oid = new ObjectId(note.getId());
        Note noteToSave = new Note(oid, note.getTitle(), note.getContent());
        this.noteRepository.save(noteToSave);
        this.noteRepository.addUserNote(username, oid);
    }

    public List<UserNotes> readNotes() {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return this.noteRepository.getUserNotes(username);
    }

    public void updateNote(ObjectId noteId, Note note) {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        if (this.noteRepository.userHasNote(username, noteId)) {
            note.setId(noteId);
            this.noteRepository.save(note);
        }
    }

    // if user has the note: deletes note from notes collection & removes note's
    // refrences from all users
    public void deleteNote(ObjectId noteId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        if (this.noteRepository.userHasNote(username, noteId)) {
            this.noteRepository.deleteById(noteId);
            this.noteRepository.deleteUserNote(username, noteId);
        }
    }

    public void shareNote(String targetUser, ObjectId noteId) {
        String sourceUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        JsonNode obj;
        try {
            obj = new ObjectMapper().readTree(targetUser);
            targetUser = obj.get("username").textValue();
            if (!userRepository.existsByUsername(targetUser))
                throw new UsernameNotFoundException("");
            logger.warn(targetUser);
            if (this.noteRepository.userHasNote(sourceUser, noteId)) {
                this.noteRepository.addUserNote(targetUser, noteId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}