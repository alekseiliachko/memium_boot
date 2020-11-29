package com.degenerates.memium.controller;

import com.degenerates.memium.exceptions.EntityNotFoundException;
import com.degenerates.memium.facade.AuthFacade;
import com.degenerates.memium.model.dao.Account;
import com.degenerates.memium.model.dao.ERole;
import com.degenerates.memium.model.dao.Role;
import com.degenerates.memium.model.dto.LogInForm;
import com.degenerates.memium.model.dto.LogInSuccess;
import com.degenerates.memium.model.dto.MeRequest;
import com.degenerates.memium.model.dto.SignupForm;
import com.degenerates.memium.repository.AccountRepository;
import com.degenerates.memium.repository.RoleRepository;
import com.degenerates.memium.security.jwt.JwtUtils;
import com.degenerates.memium.security.services.SecurityUserDetails;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

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
