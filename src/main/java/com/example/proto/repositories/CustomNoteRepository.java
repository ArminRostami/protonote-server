package com.example.proto.repositories;

import java.util.List;

import com.example.proto.models.UserNotes;

import org.bson.types.ObjectId;

public interface CustomNoteRepository {
    void addUserNote(String username, ObjectId note);

    boolean userHasNote(String username, ObjectId noteId);

    void deleteUserNote(String username, ObjectId noteId);

    List<UserNotes> getUserNotes(String username);
}