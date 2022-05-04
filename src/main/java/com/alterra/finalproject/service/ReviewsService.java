package com.alterra.finalproject.service;


import com.alterra.finalproject.constant.AppConstant;
import com.alterra.finalproject.domain.dao.*;
import com.alterra.finalproject.domain.dto.ReviewsDto;
import com.alterra.finalproject.repository.ReviewsRepository;
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
public class ReviewsService {

    @Autowired
    private ReviewsRepository reviewsRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ModelMapper mapper;

    public ResponseEntity<Object> getAllReview() {
        log.info("Executing get all review.");
        try{
            List<ReviewsDao> daoList = reviewsRepository.findAll();
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
            Optional<ReviewsDao> reviewsDao = reviewsRepository.findById(id);
            if(reviewsDao.isEmpty()) {
                log.info("review id: {} not found", id);
                return ResponseUtil.build(AppConstant.Message.NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }
            log.info("Executing get review by id success");
            return ResponseUtil.build(AppConstant.Message.SUCCESS, reviewsDao, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Happened error when get review by id. Error: {}", e.getMessage());
            log.trace("Get error when get review by id. ", e);
            throw e;
        }
    }

    public ResponseEntity<Object> addReview(ReviewsDto request) {
        log.info("Executing add review with request: {}", request);
        try{

            log.info("Get transaction by id: {}", request.getTransactionId());
            Optional<TransactionDao> transactionDao = transactionRepository.findById(request.getTransactionId());
            if (transactionDao.isEmpty()) {
                log.info("transaction [{}] not found", request.getTransactionId());
                return ResponseUtil.build(AppConstant.Message.NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }
            if (!transactionDao.get().getStatus().equals("paid")) {
                log.info("transaction [{}] not paid", request.getTransactionId());
                return ResponseUtil.build(AppConstant.Message.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            ReviewsDao reviewsDao = ReviewsDao.builder()
                    .transaction(transactionDao.get())
                    .rating(request.getRating())
                    .review(request.getReview())
                    .build();
//            reviewDao.setBooks(bookDao.get());
//            reviewDao.setCustomers(customerDao.get());
            reviewsDao = reviewsRepository.save(reviewsDao);
            log.info("Executing add review success");
            return ResponseUtil.build(AppConstant.Message.SUCCESS, mapper.map(reviewsDao, ReviewsDto.class), HttpStatus.OK);

        } catch (Exception e) {
            log.error("Happened error when add review. Error: {}", e.getMessage());
            log.trace("Get error when add review. ", e);
            throw e;   }
    }

    public ResponseEntity<Object> deleteReview(Long id) {
        log.info("Executing delete review id: {}", id);
        try{
            Optional<ReviewsDao> reviewsDao = reviewsRepository.findById(id);
            if(reviewsDao.isEmpty()) {
                log.info("review {} not found", id);
                return ResponseUtil.build(AppConstant.Message.NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }
            reviewsRepository.deleteById(id);
            log.info("Executing delete review success");
            return ResponseUtil.build(AppConstant.Message.SUCCESS, null, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Happened error when delete review. Error: {}", e.getMessage());
            log.trace("Get error when delete review. ", e);
            throw e;
        }

    }

    public ResponseEntity<Object> updateReview(Long id, ReviewsDto request) {
        log.info("Executing update review with request: {}", request);
        try {
            Optional<ReviewsDao> reviewsDao = reviewsRepository.findById(id);
            if(reviewsDao.isEmpty()) {
                log.info("review {} not found", id);
                return ResponseUtil.build(AppConstant.Message.NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }
            reviewsDao.ifPresent(res -> {
                res.setRating(request.getRating());
                res.setReview(request.getReview());
                reviewsRepository.save(res);
            });
            log.info("Executing update review success");
            return ResponseUtil.build(AppConstant.Message.SUCCESS, mapper.map(reviewsDao, ReviewsDto.class), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Happened error when update review. Error: {}", e.getMessage());
            log.trace("Get error when update review. ", e);
            throw e;
        }
    }
}
