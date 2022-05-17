package com.alterra.finalproject.service;

import com.alterra.finalproject.constant.AppConstant;
import com.alterra.finalproject.domain.common.ApiResponse;
import com.alterra.finalproject.domain.dao.TransactionDetailDao;
import com.alterra.finalproject.domain.dao.UserDao;
import com.alterra.finalproject.domain.dto.TransactionDetailDto;
import com.alterra.finalproject.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = UserService.class)
class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @Test
    void loadUser_Test() {
        UserDao userDao = UserDao.builder()
                .id(1L)
                .username("hendro")
                .build();

        when(userRepository.getDistinctTopByUsername(any())).thenReturn(userDao);

        UserDetails userDetails = userService.loadUserByUsername(any());

        assertEquals(userDao.getUsername(), userDetails.getUsername());



    }
    @Test
    void loadUserNotFound_Test() {

        when(userRepository.getDistinctTopByUsername(any())).thenReturn(null);
        assertThrows(Exception.class, () -> userService.loadUserByUsername(any()));

    }


}