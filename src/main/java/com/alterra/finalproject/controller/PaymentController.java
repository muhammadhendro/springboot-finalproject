package com.alterra.finalproject.controller;

import com.alterra.finalproject.domain.dto.CategoryDto;
import com.alterra.finalproject.domain.dto.PaymentDto;
import com.alterra.finalproject.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/payment", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping(value = "")
    public ResponseEntity<Object> getAll() {
        return paymentService.getAllPayment();
    }



    @PostMapping(value = "")
    public ResponseEntity<Object> addCategory(@RequestBody PaymentDto request) {
        return paymentService.addPayment(request);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteCategory(@PathVariable(value = "id") Long id) {
        return paymentService.deletePayment(id);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateCategory(@PathVariable(value = "id") Long id, @RequestBody PaymentDto request) {
        return paymentService.updatePayment(id, request);
    }
}
