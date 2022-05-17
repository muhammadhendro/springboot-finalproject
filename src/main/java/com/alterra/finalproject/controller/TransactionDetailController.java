package com.alterra.finalproject.controller;

import com.alterra.finalproject.domain.dto.CustomerDto;
import com.alterra.finalproject.domain.dto.TransactionDetailDto;
import com.alterra.finalproject.domain.dto.TransactionDto;
import com.alterra.finalproject.service.TransactionDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/transaction-detail", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "Transaction Detail", value = "Transaction Detail" )
public class TransactionDetailController {

    @Autowired
    private TransactionDetailService transactionDetailService;

    @ApiOperation(value = "Get all transactions detail",  response = TransactionDetailDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success get list transaction detail"),

    })
    @GetMapping(value = "")
    public ResponseEntity<Object> getAll() {
        return transactionDetailService.getAllTransactionDetail();
    }

    @ApiOperation(value = "Get transactions detail by id",  response = TransactionDetailDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success get transaction detail by id"),

    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getById(@PathVariable(value = "id") Long id){
        return transactionDetailService.getTransactionDetailById(id);
    }

    @ApiOperation(value = "Add transactions detail",  response = TransactionDetailDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success add transaction detail"),

    })
    @PostMapping(value = "")
    public ResponseEntity<Object> addTransactionDetail(@RequestBody TransactionDetailDto request) {
        return transactionDetailService.addTransactionDetail(request);
    }

    @ApiOperation(value = "Delete transactions detail",  response = TransactionDetailDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success delete transaction detail"),

    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteReview(@PathVariable(value = "id") Long id) {
        return transactionDetailService.deleteTransactionDetail(id);
    }

    @ApiOperation(value = "Update transactions detail",  response = TransactionDetailDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success update transaction detail"),

    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateReview(@PathVariable(value = "id") Long id, @RequestBody TransactionDetailDto request) {
        return transactionDetailService.updateTransactionDetail(id, request);
    }
}
