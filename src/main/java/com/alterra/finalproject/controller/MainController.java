package com.alterra.finalproject.controller;


import com.alterra.finalproject.service.MainService;
import com.alterra.finalproject.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.management.relation.Role;

@RestController
@RequestMapping(value = "/hello")
public class MainController {

    @Autowired
    private MainService mainService;

    @GetMapping(value = "")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> hello() {
        return mainService.hello();
    }
}
