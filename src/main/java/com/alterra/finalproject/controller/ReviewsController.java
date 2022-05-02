package com.alterra.finalproject.controller;

import com.alterra.finalproject.domain.dto.ReviewDto;
import com.alterra.finalproject.domain.dto.ReviewsDto;
import com.alterra.finalproject.service.ReviewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/v1/reviews", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class ReviewsController {

    @Autowired
    private ReviewsService reviewsService;

    @GetMapping(value = "")
    public ResponseEntity<Object> getAll() {
        return reviewsService.getAllReview();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getById(@PathVariable(value = "id") Long id){
        return reviewsService.getReviewById(id);
    }

    @PostMapping(value = "")
    public ResponseEntity<Object> addReview(@RequestBody ReviewsDto request) {
        try{
            return reviewsService.addReview(request);
        } catch (Exception e) {
            throw e;
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteReview(@PathVariable(value = "id") Long id) {
        return reviewsService.deleteReview(id);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateReview(@PathVariable(value = "id") Long id, @RequestBody ReviewsDto request) {
        return reviewsService.updateReview(id, request);
    }
}
