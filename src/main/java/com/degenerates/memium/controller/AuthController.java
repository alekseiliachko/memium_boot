package com.degenerates.memium.controller;

import com.degenerates.memium.facade.AuthFacade;
import com.degenerates.memium.model.dto.LogInForm;
import com.degenerates.memium.model.dto.MeRequest;
import com.degenerates.memium.model.dto.SignupForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthFacade authFacade;

    @GetMapping("/me")
    public ResponseEntity<?> usernameByToken(@RequestBody MeRequest tokenRequest) {
        return authFacade.getUsernameByToken(tokenRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LogInForm loginRequest) {
        return authFacade.logUserIn(loginRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupForm signUpRequest) {
        return authFacade.signUserUp(signUpRequest);
    }
}
