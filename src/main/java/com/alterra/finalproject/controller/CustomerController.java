package com.alterra.finalproject.controller;


import com.alterra.finalproject.domain.dto.AuthorDto;
import com.alterra.finalproject.domain.dto.CustomerDto;
import com.alterra.finalproject.domain.dto.PaymentDto;
import com.alterra.finalproject.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/customer", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "Customer", value = "Customer" )
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @ApiOperation(value = "Get all customers",  response = CustomerDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success get list customer"),
    })
    @GetMapping(value = "")
    public ResponseEntity<Object> getAll() {
        return customerService.getAllCustomer();
    }


    @ApiOperation(value = "Add new customer",  response = CustomerDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success add new customer"),
    })
    @PostMapping(value = "")
    public ResponseEntity<Object> addCustomer(@RequestBody CustomerDto request) {
        return customerService.addCustomer(request);
    }

    @ApiOperation(value = "Delete customer",  response = CustomerDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success delete customer"),
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable(value = "id") Long id) {
        return customerService.deleteCustomer(id);
    }

    @ApiOperation(value = "Update customer",  response = CustomerDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success update customer"),
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateCustomer(@PathVariable(value = "id") Long id, @RequestBody CustomerDto request) {
        return customerService.updateCustomer(id, request);
    }
}
