package com.alterra.finalproject.service;


import com.alterra.finalproject.constant.AppConstant;
import com.alterra.finalproject.domain.dao.*;
import com.alterra.finalproject.domain.dto.CustomerDto;
import com.alterra.finalproject.domain.dto.ReviewsDto;
import com.alterra.finalproject.domain.dto.TransactionDetailDto;
import com.alterra.finalproject.repository.TransactionDetailRepository;
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
public class TransactionDetailService {

    @Autowired
    private TransactionDetailRepository transactionDetailRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<Object> getAllTransactionDetail() {
        log.info("Executing get all transaction detail.");
        try{
            List<TransactionDetailDao> daoList = transactionDetailRepository.findAll();
            return ResponseUtil.build(AppConstant.Message.SUCCESS, daoList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Happened error when get all transaction detail. Error: {}", e.getMessage());
            log.trace("Get error when get all transaction detail. ", e);
            throw e;
        }
    }

    public ResponseEntity<Object> getTransactionDetailById(Long id) {
        log.info("Executing get transaction detail by id: {} ", id);
        try {
            Optional<TransactionDetailDao> transactionDetailDao = transactionDetailRepository.findById(id);
            if(transactionDetailDao.isEmpty()) {
                log.info("transaction detail id: {} not found", id);
                return ResponseUtil.build(AppConstant.Message.NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }
            log.info("Executing get transaction detail by id success");
            return ResponseUtil.build(AppConstant.Message.SUCCESS, transactionDetailDao, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Happened error when get transaction detail by id. Error: {}", e.getMessage());
            log.trace("Get error when get transaction detail by id. ", e);
            throw e;
        }
    }

    public ResponseEntity<Object> addTransactionDetail(TransactionDetailDto request) {
        log.info("Executing add transaction detail with request: {}", request);
        try{
            log.info("Get transaction by id: {}", request.getTransactionId());
            Optional<TransactionDao> transactionDao = transactionRepository.findById(request.getTransactionId());
            if (transactionDao.isEmpty()) {
                log.info("transaction [{}] not found", request.getTransactionId());
                return ResponseUtil.build(AppConstant.Message.NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            TransactionDetailDao transactionDetailDao = modelMapper.map(request, TransactionDetailDao.class);
            Integer total_price = request.getQty() * transactionDao.get().getBook().getPrice();
            transactionDetailDao.setTotalPrice(total_price);
            transactionDetailDao = transactionDetailRepository.save(transactionDetailDao);
            log.info("Executing add transaction detail success");
            return ResponseUtil.build(AppConstant.Message.SUCCESS, modelMapper.map(transactionDetailDao, TransactionDetailDto.class), HttpStatus.OK);

        } catch (Exception e) {
            log.error("Happened error when add transaction detail. Error: {}", e.getMessage());
            log.trace("Get error when add transaction detail. ", e);
            throw e;   }
    }

    public ResponseEntity<Object> deleteTransactionDetail(Long id) {
        log.info("Executing delete transaction detail id: {}", id);
        try{
            Optional<TransactionDetailDao> transactionDetailDao = transactionDetailRepository.findById(id);
            if(transactionDetailDao.isEmpty()) {
                log.info("transaction detail {} not found", id);
                return ResponseUtil.build(AppConstant.Message.NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }
            transactionDetailRepository.deleteById(id);
            log.info("Executing delete transaction detail success");
            return ResponseUtil.build(AppConstant.Message.SUCCESS, null, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Happened error when delete transaction detail. Error: {}", e.getMessage());
            log.trace("Get error when delete transaction detail. ", e);
            throw e;
        }

    }

    public ResponseEntity<Object> updateTransactionDetail(Long id, TransactionDetailDto request) {
        log.info("Executing update transaction detail with request: {}", request);
        try {
            Optional<TransactionDetailDao> transactionDetailDao = transactionDetailRepository.findById(id);
            if(transactionDetailDao.isEmpty()) {
                log.info("transaction detail {} not found", id);
                return ResponseUtil.build(AppConstant.Message.NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }
            transactionDetailDao.ifPresent(res -> {
                res.setQty(request.getQty());
                transactionDetailRepository.save(res);
            });
            log.info("Executing update transaction detail success");
            return ResponseUtil.build(AppConstant.Message.SUCCESS, modelMapper.map(transactionDetailDao, TransactionDetailDto.class), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Happened error when update transaction detail. Error: {}", e.getMessage());
            log.trace("Get error when update transaction detail. ", e);
            throw e;
        }
    }
}
