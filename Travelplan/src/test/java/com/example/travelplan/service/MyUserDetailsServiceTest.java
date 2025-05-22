package com.example.travelplan.service;


import com.example.travelplan.model.User;
import com.example.travelplan.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class MyUserDetailsServiceTest {

    @Mock
    private UserRepository userRepo;

    @InjectMocks
    private MyUserDetailsService userDetailsService;

    @Test
    void loadUserByUsername_success() {
        User mockUser = new User();
        mockUser.setUsername("testuser");
        mockUser.setPassword("encryptedPass");
        mockUser.setRoles("ROLE_USER");

        Mockito.when(userRepo.findByUsername("testuser"))
                .thenReturn(Optional.of(mockUser));

        UserDetails ud = userDetailsService.loadUserByUsername("testuser");

        assertEquals("testuser", ud.getUsername());
    }

    @Test
    void loadUserByUsername_notFound() {
        Mockito.when(userRepo.findByUsername(anyString()))
                .thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () ->
                userDetailsService.loadUserByUsername("nonexistent")
        );
    }
}