package com.alterra.finalproject.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MainService.class)
class MainServiceTest {

    @Autowired
    private MainService mainService;

    @Test
    void mainSuccess_Test() {
        ResponseEntity<Object> responseEntity = mainService.hello();
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
    }

}