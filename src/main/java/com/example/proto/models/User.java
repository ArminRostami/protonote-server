package com.example.proto.models;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "proto_users")
public class User {
    @Id
    private ObjectId _id;

    @NotBlank
    @Indexed(unique = true)
    private String username;

    @NotBlank
    private String password;

    private List<ObjectId> notes;

    @Override
    public String toString() {
        return String.format("note = [id = %s, user = %s, notes = %s]", get_id(), getUsername(), getClass());
    }

    /**
     * @return the _id
     */
    public ObjectId get_id() {
        return _id;
    }

    /**
     * @param _id the _id to set
     */
    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the notes
     */
    public List<ObjectId> getNotes() {
        return notes;
    }

    /**
     * @param notes the notes to set
     */
    public void setNotes(List<ObjectId> notes) {
        this.notes = notes;
    }

}