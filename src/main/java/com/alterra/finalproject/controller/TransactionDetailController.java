package com.alterra.finalproject.controller;

import com.alterra.finalproject.domain.dto.CustomerDto;
import com.alterra.finalproject.domain.dto.TransactionDetailDto;
import com.alterra.finalproject.domain.dto.TransactionDto;
import com.alterra.finalproject.service.TransactionDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/transaction-detail", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class TransactionDetailController {

    @Autowired
    private TransactionDetailService transactionDetailService;

    @GetMapping(value = "")
    public ResponseEntity<Object> getAll() {
        return transactionDetailService.getAllTransactionDetail();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getById(@PathVariable(value = "id") Long id){
        return transactionDetailService.getTransactionDetailById(id);
    }

    @PostMapping(value = "")
    public ResponseEntity<Object> addTransactionDetail(@RequestBody TransactionDetailDto request) {
        return transactionDetailService.addTransactionDetail(request);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteReview(@PathVariable(value = "id") Long id) {
        return transactionDetailService.deleteTransactionDetail(id);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateReview(@PathVariable(value = "id") Long id, @RequestBody TransactionDetailDto request) {
        return transactionDetailService.updateTransactionDetail(id, request);
    }
}
