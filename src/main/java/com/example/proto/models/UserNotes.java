package com.example.proto.models;

import java.util.List;

public class UserNotes {
    private List<Note> notes;

    /**
     * @return the notes
     */
    public List<Note> getNotes() {
        return notes;
    }

    /**
     * @param notes the notes to set
     */
    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
}