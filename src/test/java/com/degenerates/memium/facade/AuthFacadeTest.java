package com.degenerates.memium.facade;

import com.degenerates.memium.model.dao.Account;
import com.degenerates.memium.model.dao.AccountDetails;
import com.degenerates.memium.model.dto.LogInForm;
import com.degenerates.memium.model.dto.LogInSuccess;
import com.degenerates.memium.model.dto.SignupForm;
import com.degenerates.memium.security.jwt.JwtUtils;
import com.degenerates.memium.security.services.SecurityUserDetails;
import com.degenerates.memium.service.AccountDetailsService;
import com.degenerates.memium.service.AccountService;
import com.degenerates.memium.service.RoleService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthFacadeTest {

    @Mock
    AccountService accountService;

    @Mock
    RoleService roleService;

    @Mock
    AccountDetailsService accountDetailsService;

    @Mock
    PasswordEncoder encoder;

    @Mock
    JwtUtils jwtUtils;

    @Mock
    AuthenticationManager authenticationManager;

    @InjectMocks
    AuthFacade authFacade;

    @Mock
    Authentication authentication;

    SecurityUserDetails securityUserDetails = mock(SecurityUserDetails.class, RETURNS_DEEP_STUBS);

    @Test
    void logUserIn() {

        LogInForm logInRequest = new LogInForm();
        logInRequest.setUsername("username");
        logInRequest.setPassword("password");

        List<String> roles = Lists.list("one", "two");

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(jwtUtils.generateJwtToken(authentication)).thenReturn("TOKEN");
        when(authentication.getPrincipal()).thenReturn(securityUserDetails);
        when(securityUserDetails.getUsername()).thenReturn("username");

        assertEquals(new LogInSuccess("TOKEN", "username"),authFacade.logUserIn(logInRequest).getBody());
    }

    @Test
    public void signUserUp() {

        when(accountService.checkIfExists("username")).thenReturn(true);
        SignupForm signUpRequest = new SignupForm();
        signUpRequest.setUsername("username");

        assertEquals(HttpStatus.BAD_REQUEST, authFacade.signUserUp(signUpRequest));
    }
}
