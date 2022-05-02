package com.alterra.finalproject.service;


import com.alterra.finalproject.constant.AppConstant;
import com.alterra.finalproject.domain.dao.*;
import com.alterra.finalproject.domain.dto.BookDto;
import com.alterra.finalproject.domain.dto.ReviewDto;
import com.alterra.finalproject.domain.dto.TransactionDto;
import com.alterra.finalproject.repository.BookRepository;
import com.alterra.finalproject.repository.CustomerRepository;
import com.alterra.finalproject.repository.PaymentRepository;
import com.alterra.finalproject.repository.TransactionRepository;
import com.alterra.finalproject.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<Object> getAllTransaction() {
        log.info("Executing get all transaction.");
        try{
            List<TransactionDao> daoList = transactionRepository.findAll();
            return ResponseUtil.build(AppConstant.Message.SUCCESS, daoList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Happened error when get all transaction. Error: {}", e.getMessage());
            log.trace("Get error when get all transaction. ", e);
            throw e;
        }
    }

    public ResponseEntity<Object> getTransactionById(Long id) {
        log.info("Executing get transaction by id: {} ", id);
        try {
            Optional<TransactionDao> transactionDao = transactionRepository.findById(id);
            if(transactionDao.isEmpty()) {
                log.info("transaction id: {} not found", id);
                return ResponseUtil.build(AppConstant.Message.NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }
            log.info("Executing get transaction by id success");
            return ResponseUtil.build(AppConstant.Message.SUCCESS, transactionDao, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Happened error when get transaction by id. Error: {}", e.getMessage());
            log.trace("Get error when get transaction by id. ", e);
            throw e;
        }
    }

    public ResponseEntity<Object> addTransaction(TransactionDto request) {
        log.info("Executing add transaction with request: {}", request);
        try{
            log.info("Get customer by id: {}", request.getCustomerId());
            Optional<CustomerDao> customerDao = customerRepository.findById(request.getCustomerId());
            if (customerDao.isEmpty()) {
                log.info("customer [{}] not found", request.getCustomerId());
                return ResponseUtil.build(AppConstant.Message.NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Get book by id: {}", request.getBookId());
            Optional<BookDao> bookDao = bookRepository.findById(request.getBookId());
            if (bookDao.isEmpty()) {
                log.info("book [{}] not found", request.getBookId());
                return ResponseUtil.build(AppConstant.Message.NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Get payment by id: {}", request.getPaymentId());
            Optional<PaymentDao> paymentDao = paymentRepository.findById(request.getPaymentId());
            if (paymentDao.isEmpty()) {
                log.info("payment [{}] not found", request.getPaymentId());
                return ResponseUtil.build(AppConstant.Message.NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            TransactionDao transactionDao = TransactionDao.builder()
                    .customer(customerDao.get())
                    .book(bookDao.get())
                    .payment(paymentDao.get())
                    .status(request.getStatus())
                    .build();
//            reviewDao.setBooks(bookDao.get());
//            reviewDao.setCustomers(customerDao.get());
            transactionDao = transactionRepository.save(transactionDao);
            log.info("Executing add transaction success");
            return ResponseUtil.build(AppConstant.Message.SUCCESS, modelMapper.map(transactionDao, TransactionDto.class), HttpStatus.OK);

        } catch (Exception e) {
            log.error("Happened error when add transaction. Error: {}", e.getMessage());
            log.trace("Get error when add transaction. ", e);
            throw e;   }
    }

    public ResponseEntity<Object> deleteTransaction(Long id) {
        log.info("Executing delete transaction id: {}", id);
        try{
            Optional<TransactionDao> transactionDao = transactionRepository.findById(id);
            if(transactionDao.isEmpty()) {
                log.info("transaction {} not found", id);
                return ResponseUtil.build(AppConstant.Message.NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }
            transactionRepository.deleteById(id);
            log.info("Executing delete transaction success");
            return ResponseUtil.build(AppConstant.Message.SUCCESS, null, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Happened error when delete transaction. Error: {}", e.getMessage());
            log.trace("Get error when delete transaction. ", e);
            throw e;
        }
    }

    public ResponseEntity<Object> updateTransaction(Long id, TransactionDto request) {
        log.info("Executing update transaction with request: {}", request);
        try {
            Optional<TransactionDao> transactionDao = transactionRepository.findById(id);
            if(transactionDao.isEmpty()) {
                log.info("transaction {} not found", id);
                return ResponseUtil.build(AppConstant.Message.NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }
            transactionDao.ifPresent(res -> {
                res.setStatus(request.getStatus());
                transactionRepository.save(res);
            });
            log.info("Executing update transaction success");
            return ResponseUtil.build(AppConstant.Message.SUCCESS, modelMapper.map(transactionDao, TransactionDto.class), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Happened error when update transaction. Error: {}", e.getMessage());
            log.trace("Get error when update transaction. ", e);
            throw e;
        }
    }

}
