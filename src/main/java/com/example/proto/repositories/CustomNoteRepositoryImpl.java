package com.example.proto.repositories;

import com.example.proto.models.User;
import com.example.proto.models.UserNotes;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class CustomNoteRepositoryImpl implements CustomNoteRepository {
    private final Logger logger = LoggerFactory.getLogger(CustomNoteRepositoryImpl.class);
    private MongoTemplate mongoTemplate;

    public CustomNoteRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void addUserNote(String username, ObjectId noteId) {
        Query query = new Query(where("username").is(username));
        Update addNote = new Update().push("notes", noteId);
        this.mongoTemplate.updateFirst(query, addNote, User.class);
    }

    @Override
    public boolean userHasNote(String username, ObjectId noteId) {
        Query query = new Query(where("username").is(username));
        query.addCriteria(where("notes").in(noteId));
        boolean hasNote = this.mongoTemplate.exists(query, User.class);
        logger.warn("user has note: " + String.valueOf(hasNote));
        return hasNote;
    }

    @Override
    public void deleteUserNote(String username, ObjectId noteId) {
        Query query = new Query(where("notes").in(noteId));
        Update removeId = new Update().pull("notes", noteId);
        this.mongoTemplate.updateMulti(query, removeId, User.class);
    }

    @Override
    public List<UserNotes> getUserNotes(String username) {

        LookupOperation lookupOperation = LookupOperation.newLookup().from("proto_notes").localField("notes")
                .foreignField("_id").as("notes");
        Aggregation aggregation = Aggregation.newAggregation(Aggregation.match(where("username").is(username)),
                lookupOperation);
        List<UserNotes> results = mongoTemplate.aggregate(aggregation, "proto_users", UserNotes.class)
                .getMappedResults();
        return results;
    }

}