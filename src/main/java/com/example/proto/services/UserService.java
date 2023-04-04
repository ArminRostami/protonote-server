package com.example.proto.services;

import java.util.ArrayList;
import java.util.List;

import com.example.proto.exceptions.PasswordTooShortException;
import com.example.proto.exceptions.UsernameExistsException;
import com.example.proto.exceptions.UsernameTooShortException;
import com.example.proto.models.User;
import com.example.proto.repositories.UserRepository;

import org.bson.types.ObjectId;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void signUp(String username, String password) {

        if (username.length() < 4) {
            throw new UsernameTooShortException();
        } else if (password.length() < 4) {
            throw new PasswordTooShortException();
        } else if (userRepository.existsByUsername(username)) {
            throw new UsernameExistsException();
        }

        User userToSave = new User();
        List<ObjectId> notes = new ArrayList<>();
        userToSave.set_id(ObjectId.get());
        userToSave.setNotes(notes);
        userToSave.setUsername(username);
        userToSave.setPassword(this.bCryptPasswordEncoder.encode(password));
        // save User:
        userRepository.save(userToSave);

    }

}