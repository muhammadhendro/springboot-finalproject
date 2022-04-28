package com.alterra.finalproject.controller;

import com.alterra.finalproject.domain.dto.BookDto;
import com.alterra.finalproject.domain.dto.ReviewDto;
import com.alterra.finalproject.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/v1/review", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping(value = "")
    public ResponseEntity<Object> getAll() {
        return reviewService.getAllReview();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getById(@PathVariable(value = "id") Long id){
        return reviewService.getReviewById(id);
    }

    @PostMapping(value = "")
    public ResponseEntity<Object> addReview(@RequestBody ReviewDto request) {
        try{
            return reviewService.addReview(request);
        } catch (Exception e) {
            throw e;
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteReview(@PathVariable(value = "id") Long id) {
        return reviewService.deleteReview(id);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateReview(@PathVariable(value = "id") Long id, @RequestBody ReviewDto request) {
        return reviewService.updateReview(id, request);
    }
}
