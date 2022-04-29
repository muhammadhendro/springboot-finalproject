package com.alterra.finalproject.service;


import com.alterra.finalproject.constant.AppConstant;
import com.alterra.finalproject.domain.dao.BookDao;
import com.alterra.finalproject.domain.dao.CustomerDao;
import com.alterra.finalproject.domain.dao.PaymentDao;
import com.alterra.finalproject.domain.dto.CustomerDto;
import com.alterra.finalproject.domain.dto.PaymentDto;
import com.alterra.finalproject.repository.CustomerRepository;
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
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ModelMapper mapper;

    public ResponseEntity<Object> getAllCustomer() {
        log.info("Executing get all customer.");
        try{
            List<CustomerDao> daoList = customerRepository.findAll();
            return ResponseUtil.build(AppConstant.Message.SUCCESS, daoList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Happened error when get all customer. Error: {}", e.getMessage());
            log.trace("Get error when get all customer. ", e);
            throw e;
        }
    }

    public ResponseEntity<Object> getCustomerById(Long id) {
        log.info("Executing get customer by id: {} ", id);
        try {
            Optional<CustomerDao> customerDao = customerRepository.findById(id);
            if(customerDao.isEmpty()) {
                log.info("customer id: {} not found", id);
                return ResponseUtil.build(AppConstant.Message.NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }
            log.info("Executing get customer by id success");
            return ResponseUtil.build(AppConstant.Message.SUCCESS, customerDao, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Happened error when get customer by id. Error: {}", e.getMessage());
            log.trace("Get error when get customer by id. ", e);
            throw e;
        }
    }

    public ResponseEntity<Object> addCustomer(CustomerDto request) {
        log.info("Executing add customer with request: {}", request);
        try{
            CustomerDao customerDao = mapper.map(request, CustomerDao.class);
            customerDao = customerRepository.save(customerDao);
            log.info("Executing add customer success");
            return ResponseUtil.build(AppConstant.Message.SUCCESS, mapper.map(customerDao, CustomerDto.class), HttpStatus.OK);

        } catch (Exception e) {
            log.error("Happened error when add customer. Error: {}", e.getMessage());
            log.trace("Get error when add customer. ", e);
            throw e;   }
    }

    public ResponseEntity<Object> deleteCustomer(Long id) {
        log.info("Executing delete customer id: {}", id);
        try{
            Optional<CustomerDao> customerDao = customerRepository.findById(id);
            if(customerDao.isEmpty()) {
                log.info("customer {} not found", id);
                return ResponseUtil.build(AppConstant.Message.NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }
            customerRepository.deleteById(id);
            log.info("Executing delete customer success");
            return ResponseUtil.build(AppConstant.Message.SUCCESS, null, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Happened error when delete customer. Error: {}", e.getMessage());
            log.trace("Get error when delete customer. ", e);
            throw e;
        }

    }

    public ResponseEntity<Object> updateCustomer(Long id, CustomerDto request) {
        log.info("Executing update customer with request: {}", request);
        try {
            Optional<CustomerDao> customerDao= customerRepository.findById(id);
            if(customerDao.isEmpty()) {
                log.info("customer {} not found", id);
                return ResponseUtil.build(AppConstant.Message.NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }
            customerDao.ifPresent(res -> {
                res.setCustomerName(request.getCustomerName());
                res.setAddress(request.getAddress());
                customerRepository.save(res);
            });
            log.info("Executing update customer success");
            return ResponseUtil.build(AppConstant.Message.SUCCESS, mapper.map(customerDao, CustomerDto.class), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Happened error when update customer. Error: {}", e.getMessage());
            log.trace("Get error when update customer. ", e);
            throw e;
        }
    }
}
