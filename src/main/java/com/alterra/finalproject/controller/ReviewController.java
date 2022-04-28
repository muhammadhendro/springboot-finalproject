package com.alterra.finalproject.controller;

import com.alterra.finalproject.domain.dto.BookDto;
import com.alterra.finalproject.domain.dto.PaymentDto;
import com.alterra.finalproject.domain.dto.ReviewDto;
import com.alterra.finalproject.service.ReviewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/v1/review", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "Review", value = "Review" )
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @ApiOperation(value = "Get all reviews",  response = ReviewDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success get list review"),
    })
    @GetMapping(value = "")
    public ResponseEntity<Object> getAll() {
        return reviewService.getAllReview();
    }

    @ApiOperation(value = "Get review by id",  response = ReviewDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success get review by id"),
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getById(@PathVariable(value = "id") Long id){
        return reviewService.getReviewById(id);
    }

    @ApiOperation(value = "Add new review",  response = ReviewDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success add new review"),
    })
    @PostMapping(value = "")
    public ResponseEntity<Object> addReview(@RequestBody ReviewDto request) {
        try{
            return reviewService.addReview(request);
        } catch (Exception e) {
            throw e;
        }
    }

    @ApiOperation(value = "Delete reviews",  response = ReviewDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success delete review"),
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteReview(@PathVariable(value = "id") Long id) {
        return reviewService.deleteReview(id);
    }

    @ApiOperation(value = "Update reviews",  response = ReviewDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success update review"),
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateReview(@PathVariable(value = "id") Long id, @RequestBody ReviewDto request) {
        return reviewService.updateReview(id, request);
    }
}
