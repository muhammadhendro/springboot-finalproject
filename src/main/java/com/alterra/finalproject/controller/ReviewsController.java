package com.alterra.finalproject.controller;

import com.alterra.finalproject.domain.dto.AuthorDto;
import com.alterra.finalproject.domain.dto.ReviewsDto;
import com.alterra.finalproject.service.ReviewsService;
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
@RequestMapping(value = "/v1/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "Reviews", value = "Reviews" )
public class ReviewsController {

    @Autowired
    private ReviewsService reviewsService;

    @ApiOperation(value = "Get all reviews",  response = ReviewsDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success get list reviews"),

    })
    @GetMapping(value = "")
    public ResponseEntity<Object> getAll() {
        return reviewsService.getAllReview();
    }

    @ApiOperation(value = "Get reviews by id",  response = ReviewsDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success get reviews by id"),

    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getById(@PathVariable(value = "id") Long id){
        return reviewsService.getReviewById(id);
    }

    @ApiOperation(value = "Add reviews",  response = ReviewsDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success add reviews"),

    })
    @PostMapping(value = "")
    public ResponseEntity<Object> addReview(@RequestBody ReviewsDto request) {
        try{
            return reviewsService.addReview(request);
        } catch (Exception e) {
            throw e;
        }
    }

    @ApiOperation(value = "Delete reviews",  response = ReviewsDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success delete reviews"),

    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteReview(@PathVariable(value = "id") Long id) {
        return reviewsService.deleteReview(id);
    }

    @ApiOperation(value = "Update reviews",  response = ReviewsDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success Update reviews"),

    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateReview(@PathVariable(value = "id") Long id, @RequestBody ReviewsDto request) {
        return reviewsService.updateReview(id, request);
    }
}
