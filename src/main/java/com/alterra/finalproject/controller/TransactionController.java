package com.alterra.finalproject.controller;


import com.alterra.finalproject.domain.dto.ReviewDto;
import com.alterra.finalproject.domain.dto.ReviewsDto;
import com.alterra.finalproject.domain.dto.TransactionDto;
import com.alterra.finalproject.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/transaction", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping(value = "")
    public ResponseEntity<Object> getAll() {
        return transactionService.getAllTransaction();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getById(@PathVariable(value = "id") Long id){
        return transactionService.getTransactionById(id);
    }

    @PostMapping(value = "")
    public ResponseEntity<Object> addTransaction(@RequestBody TransactionDto request) {
        try{
            return transactionService.addTransaction(request);
        } catch (Exception e) {
            throw e;
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteReview(@PathVariable(value = "id") Long id) {
        return transactionService.deleteTransaction(id);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateReview(@PathVariable(value = "id") Long id, @RequestBody TransactionDto request) {
        return transactionService.updateTransaction(id, request);
    }
}
