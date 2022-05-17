package com.alterra.finalproject.controller;


import com.alterra.finalproject.domain.dto.ReviewsDto;
import com.alterra.finalproject.domain.dto.TransactionDto;
import com.alterra.finalproject.service.TransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/transaction", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "Transaction", value = "Transaction" )
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @ApiOperation(value = "Get all transactions",  response = TransactionDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success get list transaction"),

    })
    @GetMapping(value = "")
    public ResponseEntity<Object> getAll() {
        return transactionService.getAllTransaction();
    }

    @ApiOperation(value = "Get transactions by id",  response = TransactionDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success get transaction by id"),

    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getById(@PathVariable(value = "id") Long id){
        return transactionService.getTransactionById(id);
    }

    @ApiOperation(value = "Add transactions",  response = TransactionDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success add transaction"),

    })
    @PostMapping(value = "")
    public ResponseEntity<Object> addTransaction(@RequestBody TransactionDto request) {
        try{
            return transactionService.addTransaction(request);
        } catch (Exception e) {
            throw e;
        }
    }

    @ApiOperation(value = "Delete transactions",  response = TransactionDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success delete transaction"),

    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteReview(@PathVariable(value = "id") Long id) {
        return transactionService.deleteTransaction(id);
    }

    @ApiOperation(value = "Update transactions",  response = TransactionDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success update transaction"),

    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateReview(@PathVariable(value = "id") Long id, @RequestBody TransactionDto request) {
        return transactionService.updateTransaction(id, request);
    }
}
