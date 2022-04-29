package com.alterra.finalproject.service;

import com.alterra.finalproject.constant.AppConstant;
import com.alterra.finalproject.domain.common.ApiResponse;
import com.alterra.finalproject.domain.dao.*;
import com.alterra.finalproject.domain.dto.BookDto;
import com.alterra.finalproject.domain.dto.ReviewDto;
import com.alterra.finalproject.repository.BookRepository;
import com.alterra.finalproject.repository.CustomerRepository;
import com.alterra.finalproject.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ReviewService.class)
class ReviewServiceTest {

    @MockBean
    private ReviewRepository reviewRepository;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private BookRepository bookRepository;

    @Autowired
    private ReviewService reviewService;

    @Test
    void addReviewSuccess_Test() {
        BookDao bookDao = BookDao.builder()
                .id(1L)
                .build();

        CustomerDao customerDao = CustomerDao.builder()
                .id(1L)
                .build();

        ReviewDao reviewDao = ReviewDao.builder()
                .id(1L)
                .build();

        ReviewDto reviewDto = ReviewDto.builder()
                .id(1L)
                .bookId(1L)
                .customerId(1L)
                .review("mantap")
                .build();

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(bookDao));
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customerDao));
        when(modelMapper.map(any(), eq(ReviewDao.class))).thenReturn(reviewDao);
        when(modelMapper.map(any(), eq(ReviewDto.class))).thenReturn(reviewDto);
        when(reviewRepository.save(any())).thenReturn(reviewDao);

        ResponseEntity<Object> responseEntity = reviewService.addReview(ReviewDto.builder()
                .id(1L)
                .bookId(1L)
                .customerId(1L)
                .review("mantap")
                .build());

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        ReviewDto reviewDto1 = (ReviewDto) Objects.requireNonNull(apiResponse).getData();

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.SUCCESS, Objects.requireNonNull(apiResponse).getMessage());
        assertEquals(reviewDto.getReview(), reviewDto1.getReview());
        assertEquals(reviewDto.getCustomerId(), reviewDto1.getCustomerId());

    }

    @Test
    void addReviewBookIsEmpty_Test() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(CustomerDao.builder()
                .id(1L)
                .build()));
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());


        ResponseEntity<Object> responseEntity = reviewService.addReview(ReviewDto.builder()
                .id(1L)
                .bookId(1L)
                .customerId(1L)
                .review("mantap")
                .build());

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    void addReviewCustomerIsEmpty_Test() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Object> responseEntity = reviewService.addReview(ReviewDto.builder()
                .id(1L)
                .bookId(1L)
                .customerId(1L)
                .review("mantap")
                .build());

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    void addReviewException_Test() {
        when(customerRepository.findById(anyLong())).thenThrow(NullPointerException.class);
        assertThrows(Exception.class, () -> reviewService.addReview(ReviewDto.builder()
                .id(1L)
                .bookId(1L)
                .customerId(1L)
                .review("mantap")
                .build()));
    }

    @Test
    void getAllReviewSuccess_Test() {

        ReviewDao reviewDao = ReviewDao.builder()
                .id(1L)
                .build();
        when(reviewRepository.findAll()).thenReturn(List.of(reviewDao));
        when(modelMapper.map(any(), eq(ReviewDto.class))).thenReturn(ReviewDto.builder()
                .id(1L)
                .bookId(1L)
                .customerId(1L)
                .review("mantap")
                .build());
        ResponseEntity<Object> responseEntity = reviewService.getAllReview();

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        List<ReviewDto> reviewDtos = (List<ReviewDto>) apiResponse.getData();

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.SUCCESS, Objects.requireNonNull(apiResponse).getMessage());
        assertEquals(1, reviewDtos.size());

    }

    @Test
    void getAllReviewException_Test() {
        when(reviewRepository.findAll()).thenThrow(NullPointerException.class);
        assertThrows(Exception.class, () -> reviewService.getAllReview());
    }

    @Test
    void getReviewByIdSuccess_Test() {

        ReviewDao reviewDao = ReviewDao.builder()
                .id(1L)
                .build();
        when(reviewRepository.findById(anyLong())).thenReturn(Optional.of(reviewDao));
        when(modelMapper.map(any(), eq(ReviewDto.class))).thenReturn(ReviewDto.builder()
                .id(1L)
                .bookId(1L)
                .customerId(1L)
                .review("mantap")
                .build());
        ResponseEntity<Object> responseEntity = reviewService.getReviewById(1L);

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.SUCCESS, Objects.requireNonNull(apiResponse).getMessage());


    }

    @Test
    void getReviewByIdNotFound_Test() {
        when(reviewRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Object> responseEntity = reviewService.getReviewById(1L);

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    void getReviewByIdException_Test() {
        when(reviewRepository.findById(anyLong())).thenThrow(NullPointerException.class);
        assertThrows(Exception.class, () -> reviewService.getReviewById(1L));
    }

    @Test
    void deleteReviewSuccess_Test() {
        when(reviewRepository.findById(anyLong())).thenReturn(Optional.of(ReviewDao.builder()
                .id(1L)
                .build()));
        doNothing().when(reviewRepository).delete(any());

        ResponseEntity<Object> responseEntity = reviewService.deleteReview(1L);

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.SUCCESS, Objects.requireNonNull(apiResponse).getMessage());

    }
    @Test
    void deleteReviewNotFound_Test() {
        when(reviewRepository.findById(anyLong())).thenReturn(Optional.empty());
        ResponseEntity<Object> responseEntity = reviewService.deleteReview(1L);

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    void deleteReviewException_Test() {
        when(reviewRepository.findById(anyLong())).thenThrow(NullPointerException.class);
        assertThrows(Exception.class, () -> reviewService.deleteReview(1L));
    }

    @Test
    void updateReviewSuccess_Test() {
        ReviewDao reviewDao = ReviewDao.builder()
                .id(1L)
                .build();

        ReviewDto reviewDto = ReviewDto.builder()
                .id(1L)
                .bookId(1L)
                .customerId(1L)
                .review("mantap")
                .build();

        when(reviewRepository.findById(anyLong())).thenReturn(Optional.of(reviewDao));
        when(modelMapper.map(any(), eq(ReviewDao.class))).thenReturn(reviewDao);
        when(modelMapper.map(any(), eq(ReviewDto.class))).thenReturn(reviewDto);


        ResponseEntity<Object> responseEntity = reviewService.updateReview(anyLong(), ReviewDto.builder()
                .id(1L)
                .bookId(1L)
                .customerId(1L)
                .review("mantap")
                .build());


        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        ReviewDto reviewDto1 = (ReviewDto) Objects.requireNonNull(apiResponse).getData();

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.SUCCESS, Objects.requireNonNull(apiResponse).getMessage());
        assertEquals(reviewDto.getReview(), reviewDto1.getReview());


    }

    @Test
    void updateReviewNotFound_Test() {
        when(reviewRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Object> responseEntity = reviewService.updateReview(anyLong(), ReviewDto.builder()
                .id(1L)
                .bookId(1L)
                .customerId(1L)
                .review("mantap")
                .build());

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    void updateReviewException_Test() {
        when(reviewRepository.findById(anyLong())).thenThrow(NullPointerException.class);
        assertThrows(Exception.class, () -> reviewService.updateReview(anyLong(), ReviewDto.builder()
                .id(1L)
                .bookId(1L)
                .customerId(1L)
                .review("mantap")
                .build()));
    }


}