package com.degenerates.memium.facade;

import com.degenerates.memium.model.dao.Account;
import com.degenerates.memium.model.dto.LogInForm;
import com.degenerates.memium.model.dto.LogInSuccess;
import com.degenerates.memium.model.dto.MeRequest;
import com.degenerates.memium.model.dto.SignupForm;
import com.degenerates.memium.security.jwt.JwtUtils;
import com.degenerates.memium.security.services.SecurityUserDetails;
import com.degenerates.memium.service.AccountDetailsService;
import com.degenerates.memium.service.AccountService;
import com.degenerates.memium.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class AuthFacade {

    @Autowired
    AccountService accountService;

    @Autowired
    RoleService roleService;

    @Autowired
    AccountDetailsService accountDetailsService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    AuthenticationManager authenticationManager;

    public ResponseEntity<?> logUserIn(LogInForm logInRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(logInRequest.getUsername(), logInRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        SecurityUserDetails userDetails = (SecurityUserDetails) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new LogInSuccess(jwt, userDetails.getUsername()));
    }

    public ResponseEntity<?> signUserUp(SignupForm signUpRequest) {

        if (accountService.checkIfExists(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }


        // Create new user's account
        Account account = new Account();

        account.setAccountId(UUID.randomUUID());
        account.setUsername(signUpRequest.getUsername());
        account.setEmail(signUpRequest.getEmail());
        account.setCreated(new Date());

        account.setRoles(roleService.getDefaultRoleSet());

        account.setPassword(encoder.encode(signUpRequest.getPassword()));

        accountService.save(account);

        return ResponseEntity.ok("User registered successfully!");
    }

    public ResponseEntity<?> getUsernameByToken(MeRequest tokenRequest) {
        String username = jwtUtils.getUserNameFromJwtToken(tokenRequest.getToken());
        return ResponseEntity.ok(username);
    };
}
