package com.alterra.finalproject.controller;

import com.alterra.finalproject.domain.dto.CategoryDto;
import com.alterra.finalproject.domain.dto.CustomerDto;
import com.alterra.finalproject.domain.dto.PaymentDto;
import com.alterra.finalproject.service.PaymentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/payment", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "Payment Method", value = "Payment Method" )
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @ApiOperation(value = "Get all payment",  response = PaymentDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success get list payment"),
    })
    @GetMapping(value = "")
    public ResponseEntity<Object> getAll() {
        return paymentService.getAllPayment();
    }

    @ApiOperation(value = "Add new payment",  response = PaymentDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success add new payment"),
    })
    @PostMapping(value = "")
    public ResponseEntity<Object> addPayment(@RequestBody PaymentDto request) {
        return paymentService.addPayment(request);
    }

    @ApiOperation(value = "Delete payment",  response = PaymentDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success delete payment"),
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deletePayment(@PathVariable(value = "id") Long id) {
        return paymentService.deletePayment(id);
    }

    @ApiOperation(value = "Update payment",  response = PaymentDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success update payment"),
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updatePayment(@PathVariable(value = "id") Long id, @RequestBody PaymentDto request) {
        return paymentService.updatePayment(id, request);
    }
}
