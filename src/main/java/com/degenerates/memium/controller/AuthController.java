package com.degenerates.memium.controller;

import com.degenerates.memium.facade.AuthFacade;
import com.degenerates.memium.model.dto.LogInForm;
import com.degenerates.memium.model.dto.LogInSuccess;
import com.degenerates.memium.model.dto.MeRequest;
import com.degenerates.memium.model.dto.SignupForm;
import io.swagger.v3.oas.annotations.headers.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthFacade authFacade;

    @PostMapping("/login")
    public ResponseEntity<LogInSuccess> authenticateUser(@RequestBody LogInForm loginRequest) {
        return authFacade.logUserIn(loginRequest);
    }

    @PostMapping("/signup")
    public HttpStatus registerUser(@RequestBody SignupForm signUpRequest) {
        return authFacade.signUserUp(signUpRequest);
    }
}
