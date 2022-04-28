package com.alterra.finalproject.controller;


import com.alterra.finalproject.domain.dto.CustomerDto;
import com.alterra.finalproject.domain.dto.PaymentDto;
import com.alterra.finalproject.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/customer", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping(value = "")
    public ResponseEntity<Object> getAll() {
        return customerService.getAllCustomer();
    }



    @PostMapping(value = "")
    public ResponseEntity<Object> addCategory(@RequestBody CustomerDto request) {
        return customerService.addCustomer(request);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteCategory(@PathVariable(value = "id") Long id) {
        return customerService.deleteCustomer(id);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateCategory(@PathVariable(value = "id") Long id, @RequestBody CustomerDto request) {
        return customerService.updateCustomer(id, request);
    }
}
