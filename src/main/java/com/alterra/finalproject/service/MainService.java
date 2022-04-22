package com.alterra.finalproject.service;

import com.alterra.finalproject.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MainService {

    public ResponseEntity<Object> hello(){
        try{
            return ResponseUtil.build("Hello World", null, HttpStatus.OK);
        }
        catch (Exception e) {
            throw e;
        }
    }


}
