package com.alterra.finalproject.service;

import com.alterra.finalproject.constant.AppConstant;
import com.alterra.finalproject.domain.common.ApiResponse;
import com.alterra.finalproject.domain.dao.*;
import com.alterra.finalproject.domain.dto.ReviewsDto;
import com.alterra.finalproject.repository.ReviewsRepository;
import com.alterra.finalproject.repository.TransactionRepository;
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
@SpringBootTest(classes = ReviewsService.class)
class ReviewsServiceTest {

    @MockBean
    private ReviewsRepository reviewsRepository;

    @MockBean
    private TransactionRepository transactionRepository;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private ReviewsService reviewsService;

    @Test
    void addReviewSuccess_Test() {


        TransactionDao transactionDao = TransactionDao.builder()
                .id(1L)
                .status("paid")
                .build();

        ReviewsDao reviewDao = ReviewsDao.builder()
                .id(1L)
                .build();

        ReviewsDto reviewDto = ReviewsDto.builder()
                .id(1L)
                .transactionId(1L)
                .review("mantap")
                .build();


        when(transactionRepository.findById(anyLong())).thenReturn(Optional.of(transactionDao));
        when(modelMapper.map(any(), eq(ReviewsDao.class))).thenReturn(reviewDao);
        when(modelMapper.map(any(), eq(ReviewsDto.class))).thenReturn(reviewDto);
        when(reviewsRepository.save(any())).thenReturn(reviewDao);

        ResponseEntity<Object> responseEntity = reviewsService.addReview(ReviewsDto.builder()
                .id(1L)
                .transactionId(1L)
                .review("mantap")
                .build());

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        ReviewsDto reviewDto1 = (ReviewsDto) Objects.requireNonNull(apiResponse).getData();

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.SUCCESS, Objects.requireNonNull(apiResponse).getMessage());
        assertEquals(reviewDto.getReview(), reviewDto1.getReview());
        assertEquals(reviewDto.getTransactionId(), reviewDto1.getTransactionId());

    }

    @Test
    void addReviewTransactionIsEmpty_Test() {
        when(transactionRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Object> responseEntity = reviewsService.addReview(ReviewsDto.builder()
                .id(1L)
                .transactionId(1L)
                .review("mantap")
                .build());

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());
    }

    @Test
    void addReviewTransactionNotPaid_Test() {
        TransactionDao transactionDao = TransactionDao.builder()
                .id(1L)
                .status("unpaid")
                .build();
        when(transactionRepository.findById(anyLong())).thenReturn(Optional.of(transactionDao));

        ResponseEntity<Object> responseEntity = reviewsService.addReview(ReviewsDto.builder()
                .id(1L)
                .transactionId(1L)
                .review("mantap")
                .build());

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.UNKNOWN_ERROR, Objects.requireNonNull(apiResponse).getMessage());
    }

    @Test
    void addReviewException_Test() {
        when(transactionRepository.findById(anyLong())).thenThrow(NullPointerException.class);
        assertThrows(Exception.class, () -> reviewsService.addReview(ReviewsDto.builder()
                .id(1L)
                .transactionId(1L)
                .review("mantap")
                .build()));
    }

    @Test
    void getAllReviewSuccess_Test() {

        ReviewsDao reviewDao = ReviewsDao.builder()
                .id(1L)
                .build();
        when(reviewsRepository.findAll()).thenReturn(List.of(reviewDao));
        when(modelMapper.map(any(), eq(ReviewsDto.class))).thenReturn(ReviewsDto.builder()
                .id(1L)
                .transactionId(1L)
                .review("mantap")
                .build());
        ResponseEntity<Object> responseEntity = reviewsService.getAllReview();

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        List<ReviewsDto> reviewDtos = (List<ReviewsDto>) apiResponse.getData();

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.SUCCESS, Objects.requireNonNull(apiResponse).getMessage());
        assertEquals(1, reviewDtos.size());

    }

    @Test
    void getAllReviewException_Test() {
        when(reviewsRepository.findAll()).thenThrow(NullPointerException.class);
        assertThrows(Exception.class, () -> reviewsService.getAllReview());
    }

    @Test
    void getReviewByIdSuccess_Test() {

        ReviewsDao reviewDao = ReviewsDao.builder()
                .id(1L)
                .build();
        when(reviewsRepository.findById(anyLong())).thenReturn(Optional.of(reviewDao));
        when(modelMapper.map(any(), eq(ReviewsDto.class))).thenReturn(ReviewsDto.builder()
                .id(1L)
                .transactionId(1L)
                .review("mantap")
                .build());
        ResponseEntity<Object> responseEntity = reviewsService.getReviewById(1L);

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.SUCCESS, Objects.requireNonNull(apiResponse).getMessage());
    }

    @Test
    void getReviewByIdNotFound_Test() {
        when(reviewsRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Object> responseEntity = reviewsService.getReviewById(1L);

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    void getReviewByIdException_Test() {
        when(reviewsRepository.findById(anyLong())).thenThrow(NullPointerException.class);
        assertThrows(Exception.class, () -> reviewsService.getReviewById(1L));
    }

    @Test
    void deleteReviewSuccess_Test() {
        when(reviewsRepository.findById(anyLong())).thenReturn(Optional.of(ReviewsDao.builder()
                .id(1L)
                .build()));
        doNothing().when(reviewsRepository).delete(any());

        ResponseEntity<Object> responseEntity = reviewsService.deleteReview(1L);

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.SUCCESS, Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    void deleteReviewNotFound_Test() {
        when(reviewsRepository.findById(anyLong())).thenReturn(Optional.empty());
        ResponseEntity<Object> responseEntity = reviewsService.deleteReview(1L);

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    void deleteReviewException_Test() {
        when(reviewsRepository.findById(anyLong())).thenThrow(NullPointerException.class);
        assertThrows(Exception.class, () -> reviewsService.deleteReview(1L));
    }

    @Test
    void updateReviewSuccess_Test() {
        ReviewsDao reviewDao = ReviewsDao.builder()
                .id(1L)
                .build();

        ReviewsDto reviewDto = ReviewsDto.builder()
                .id(1L)
                .transactionId(1L)
                .review("mantap")
                .build();

        when(reviewsRepository.findById(anyLong())).thenReturn(Optional.of(reviewDao));
        when(modelMapper.map(any(), eq(ReviewsDao.class))).thenReturn(reviewDao);
        when(modelMapper.map(any(), eq(ReviewsDto.class))).thenReturn(reviewDto);


        ResponseEntity<Object> responseEntity = reviewsService.updateReview(anyLong(), ReviewsDto.builder()
                .id(1L)
                        .transactionId(1L)
                .review("mantap")
                .build());


        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        ReviewsDto reviewDto1 = (ReviewsDto) Objects.requireNonNull(apiResponse).getData();

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.SUCCESS, Objects.requireNonNull(apiResponse).getMessage());
        assertEquals(reviewDto.getReview(), reviewDto1.getReview());
    }

    @Test
    void updateReviewNotFound_Test() {
        when(reviewsRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Object> responseEntity = reviewsService.updateReview(anyLong(), ReviewsDto.builder()
                .id(1L)
                        .transactionId(1L)
                .review("mantap")
                .build());

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    void updateReviewException_Test() {
        when(reviewsRepository.findById(anyLong())).thenThrow(NullPointerException.class);
        assertThrows(Exception.class, () -> reviewsService.updateReview(anyLong(), ReviewsDto.builder()
                .id(1L)
                        .transactionId(1L)
                .review("mantap")
                .build()));
    }

}