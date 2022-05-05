package com.alterra.finalproject.service;

import com.alterra.finalproject.config.security.JwtTokenProvider;
import com.alterra.finalproject.domain.dao.UserDao;
import com.alterra.finalproject.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AuthService.class)
class AuthServiceTest {


    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

//    @Test
//    void register_Test() {
//        UserDao userDao = UserDao.builder()
//                .username("hendro")
//                .password("123")
//                .build();
//        when(userRepository.save(any())).thenReturn(userDao);
//
//        UserDetails userDetails = userService.loadUserByUsername(any());
//
//        assertEquals(userDao.getUsername(), userDetails.getUsername());
//
//
//    }

}