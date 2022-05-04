package com.alterra.finalproject.controller;

import com.alterra.finalproject.constant.AppConstant;
import com.alterra.finalproject.domain.payload.TokenResponse;
import com.alterra.finalproject.domain.payload.UsernamePassword;
import com.alterra.finalproject.service.AuthService;
import com.alterra.finalproject.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {


    private final AuthService authService;

    @PostMapping(value="/register")
    public ResponseEntity<?> register(@RequestBody UsernamePassword req) {
        authService.register(req);
//        return ResponseEntity.ok().build();
        return ResponseUtil.build(AppConstant.Message.SUCCESS, req, HttpStatus.OK);
    }

    @PostMapping(value="/login")
    public ResponseEntity<?> login(@RequestBody UsernamePassword req) {
        TokenResponse token = authService.generateToken(req);
        return ResponseUtil.build(AppConstant.Message.SUCCESS, token, HttpStatus.OK);
    }
}
