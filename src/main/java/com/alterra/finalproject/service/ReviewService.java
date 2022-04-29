package com.alterra.finalproject.service;


import com.alterra.finalproject.constant.AppConstant;
import com.alterra.finalproject.domain.dao.*;
import com.alterra.finalproject.domain.dto.BookDto;
import com.alterra.finalproject.domain.dto.ReviewDto;
import com.alterra.finalproject.repository.BookRepository;
import com.alterra.finalproject.repository.CustomerRepository;
import com.alterra.finalproject.repository.ReviewRepository;
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
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ModelMapper mapper;

    public ResponseEntity<Object> getAllReview() {
        log.info("Executing get all review.");
        try{
            List<ReviewDao> daoList = reviewRepository.findAll();
            return ResponseUtil.build(AppConstant.Message.SUCCESS, daoList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Happened error when get all review. Error: {}", e.getMessage());
            log.trace("Get error when get all review. ", e);
            throw e;
        }
    }

    public ResponseEntity<Object> getReviewById(Long id) {
        log.info("Executing get review by id: {} ", id);
        try {
            Optional<ReviewDao> reviewDao = reviewRepository.findById(id);
            if(reviewDao.isEmpty()) {
                log.info("review id: {} not found", id);
                return ResponseUtil.build(AppConstant.Message.NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }
            log.info("Executing get review by id success");
            return ResponseUtil.build(AppConstant.Message.SUCCESS, reviewDao, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Happened error when get review by id. Error: {}", e.getMessage());
            log.trace("Get error when get review by id. ", e);
            throw e;
        }
    }

    public ResponseEntity<Object> addReview(ReviewDto request) {
        log.info("Executing add review with request: {}", request);
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
            ReviewDao reviewDao = ReviewDao.builder()
                    .customers(customerDao.get())
                    .books(bookDao.get())
                    .rating(request.getRating())
                    .review(request.getReview())
                    .build();
//            reviewDao.setBooks(bookDao.get());
//            reviewDao.setCustomers(customerDao.get());
            reviewDao = reviewRepository.save(reviewDao);
            log.info("Executing add review success");
            return ResponseUtil.build(AppConstant.Message.SUCCESS, mapper.map(reviewDao, ReviewDto.class), HttpStatus.OK);

        } catch (Exception e) {
            log.error("Happened error when add review. Error: {}", e.getMessage());
            log.trace("Get error when add review. ", e);
            throw e;   }
    }

    public ResponseEntity<Object> deleteReview(Long id) {
        log.info("Executing delete review id: {}", id);
        try{
            Optional<ReviewDao> reviewDao = reviewRepository.findById(id);
            if(reviewDao.isEmpty()) {
                log.info("review {} not found", id);
                return ResponseUtil.build(AppConstant.Message.NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }
            reviewRepository.deleteById(id);
            log.info("Executing delete review success");
            return ResponseUtil.build(AppConstant.Message.SUCCESS, null, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Happened error when delete review. Error: {}", e.getMessage());
            log.trace("Get error when delete review. ", e);
            throw e;
        }

    }
    public ResponseEntity<Object> updateReview(Long id, ReviewDto request) {
        log.info("Executing update review with request: {}", request);
        try {
            Optional<ReviewDao> reviewDao = reviewRepository.findById(id);
            if(reviewDao.isEmpty()) {
                log.info("review {} not found", id);
                return ResponseUtil.build(AppConstant.Message.NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }
            reviewDao.ifPresent(res -> {
               res.setRating(request.getRating());
               res.setReview(request.getReview());
               reviewRepository.save(res);
            });
            log.info("Executing update review success");
            return ResponseUtil.build(AppConstant.Message.SUCCESS, mapper.map(reviewDao, ReviewDto.class), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Happened error when update review. Error: {}", e.getMessage());
            log.trace("Get error when update review. ", e);
            throw e;
        }
    }

}
