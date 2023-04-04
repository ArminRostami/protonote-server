package com.example.proto.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.example.proto.repositories.UserRepository;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // method that retrieves user data from database and returns it
        com.example.proto.models.User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(user.getUsername(), user.getPassword(), emptyList());
    }

    private Collection<? extends GrantedAuthority> emptyList() {
        List<GrantedAuthority> roles = new ArrayList<>();
        return roles;
    }

}