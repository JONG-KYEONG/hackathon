package com.hackathon.server.user.controller;

import com.hackathon.server.exception.ResourceNotFoundException;
import com.hackathon.server.oauth.security.CurrentUser;
import com.hackathon.server.oauth.security.UserPrincipal;
import com.hackathon.server.user.model.User;
import com.hackathon.server.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }
}
