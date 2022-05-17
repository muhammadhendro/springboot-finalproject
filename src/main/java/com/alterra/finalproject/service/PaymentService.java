package com.alterra.finalproject.service;

import com.alterra.finalproject.constant.AppConstant;
import com.alterra.finalproject.domain.dao.AuthorDao;
import com.alterra.finalproject.domain.dao.BookDao;
import com.alterra.finalproject.domain.dao.CategoryDao;
import com.alterra.finalproject.domain.dao.PaymentDao;
import com.alterra.finalproject.domain.dto.BookDto;
import com.alterra.finalproject.domain.dto.PaymentDto;
import com.alterra.finalproject.repository.PaymentRepository;
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
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ModelMapper mapper;

    public ResponseEntity<Object> getAllPayment() {
        log.info("Executing get all payment.");
        try{
            List<PaymentDao> daoList = paymentRepository.findAll();
            return ResponseUtil.build(AppConstant.Message.SUCCESS, daoList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Happened error when get all payment. Error: {}", e.getMessage());
            log.trace("Get error when get all payment. ", e);
            throw e;
        }
    }

    public ResponseEntity<Object> addPayment(PaymentDto request) {
        log.info("Executing add payment with request: {}", request);
        try{
            PaymentDao paymentDao = mapper.map(request, PaymentDao.class);
            paymentDao = paymentRepository.save(paymentDao);
            log.info("Executing add payment success");
            return ResponseUtil.build(AppConstant.Message.SUCCESS, mapper.map(paymentDao, PaymentDto.class), HttpStatus.OK);

        } catch (Exception e) {
            log.error("Happened error when add payment. Error: {}", e.getMessage());
            log.trace("Get error when add payment. ", e);
            throw e;   }
    }

    public ResponseEntity<Object> deletePayment(Long id) {
        log.info("Executing delete payment id: {}", id);
        try{
            Optional<PaymentDao> paymentDao = paymentRepository.findById(id);
            if(paymentDao.isEmpty()) {
                log.info("Payment {} not found", id);
                return ResponseUtil.build(AppConstant.Message.NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }
            paymentRepository.deleteById(id);
            log.info("Executing delete payment success");
            return ResponseUtil.build(AppConstant.Message.SUCCESS, null, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Happened error when delete payment. Error: {}", e.getMessage());
            log.trace("Get error when delete payment. ", e);
            throw e;
        }

    }

    public ResponseEntity<Object> updatePayment(Long id, PaymentDto request) {
        log.info("Executing update payment with request: {}", request);
        try {
            Optional<PaymentDao> paymentDao= paymentRepository.findById(id);
            if(paymentDao.isEmpty()) {
                log.info("Payment {} not found", id);
                return ResponseUtil.build(AppConstant.Message.NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }
            paymentDao.ifPresent(res -> {
                res.setPaymentName(request.getPaymentName());
                paymentRepository.save(res);
            });
            log.info("Executing update payment success");
            return ResponseUtil.build(AppConstant.Message.SUCCESS, mapper.map(paymentDao, PaymentDto.class), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Happened error when update payment. Error: {}", e.getMessage());
            log.trace("Get error when update payment. ", e);
            throw e;
        }
    }


}
