package com.alterra.finalproject.service;

import com.alterra.finalproject.constant.AppConstant;
import com.alterra.finalproject.domain.common.ApiResponse;
import com.alterra.finalproject.domain.dao.*;
import com.alterra.finalproject.domain.dto.TransactionDetailDto;
import com.alterra.finalproject.domain.dto.TransactionDto;
import com.alterra.finalproject.repository.TransactionDetailRepository;
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
@SpringBootTest(classes = TransactionDetailService.class)
class TransactionDetailServiceTest {

    @MockBean
    private TransactionDetailRepository transactionDetailRepository;

    @MockBean
    private TransactionRepository transactionRepository;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private TransactionDetailService transactionDetailService;

    @Test
    void addTransactionDetailSuccess_Test() {


        TransactionDetailDao transactionDetailDao = TransactionDetailDao.builder()
                .id(1L)
                .build();

        TransactionDao transactionDao = TransactionDao.builder()
                .id(1L)
                .book(BookDao.builder().price(2000).build())
                .build();

        TransactionDetailDto transactionDetailDto = TransactionDetailDto.builder()
                .id(1L)
                .transactionId(1L)
                .qty(5)
                .build();


        when(transactionRepository.findById(anyLong())).thenReturn(Optional.of(transactionDao));
        when(modelMapper.map(any(), eq(TransactionDetailDao.class))).thenReturn(transactionDetailDao);
        when(modelMapper.map(any(), eq(TransactionDetailDto.class))).thenReturn(transactionDetailDto);
        when(transactionDetailRepository.save(any())).thenReturn(transactionDetailDao);

        ResponseEntity<Object> responseEntity = transactionDetailService.addTransactionDetail(TransactionDetailDto.builder()
                .id(1L)
                .transactionId(1L)
                .qty(5)
                .build());

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        TransactionDetailDto transactionDto1 = (TransactionDetailDto) Objects.requireNonNull(apiResponse).getData();

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.SUCCESS, Objects.requireNonNull(apiResponse).getMessage());
        assertEquals(transactionDetailDto.getQty(), transactionDto1.getQty());
        assertEquals(transactionDetailDto.getTransactionId(), transactionDto1.getTransactionId());

    }

    @Test
    void addTransactionDeatilTransactionIsEmpty_Test() {
        when(transactionRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Object> responseEntity = transactionDetailService.addTransactionDetail(TransactionDetailDto.builder()
                .id(1L)
                .transactionId(1L)
                .qty(5)
                .build());

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    void addTransactionDetailException_Test() {
        when(transactionRepository.findById(anyLong())).thenThrow(NullPointerException.class);
        assertThrows(Exception.class, () -> transactionDetailService.addTransactionDetail(TransactionDetailDto.builder()
                .id(1L)
                .transactionId(1L)
                .qty(5)
                .build()));
    }

    @Test
    void getAllTransactionDetailSuccess_Test() {

        TransactionDetailDao transactionDetailDao = TransactionDetailDao.builder()
                .id(1L)
                .build();
        when(transactionDetailRepository.findAll()).thenReturn(List.of(transactionDetailDao));
        when(modelMapper.map(any(), eq(TransactionDetailDto.class))).thenReturn(TransactionDetailDto.builder()
                .id(1L)
                .transactionId(1L)
                .qty(5)
                .build());
        ResponseEntity<Object> responseEntity = transactionDetailService.getAllTransactionDetail();

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        List<TransactionDetailDto> transactionDtos = (List<TransactionDetailDto>) apiResponse.getData();

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.SUCCESS, Objects.requireNonNull(apiResponse).getMessage());
        assertEquals(1, transactionDtos.size());

    }

    @Test
    void getAllTransactionDetailException_Test() {
        when(transactionDetailRepository.findAll()).thenThrow(NullPointerException.class);
        assertThrows(Exception.class, () -> transactionDetailService.getAllTransactionDetail());
    }

    @Test
    void getTranscationDetailByIdSuccess_Test() {

        TransactionDetailDao transactionDetailDao = TransactionDetailDao.builder()
                .id(1L)
                .build();
        when(transactionDetailRepository.findById(anyLong())).thenReturn(Optional.of(transactionDetailDao));
        when(modelMapper.map(any(), eq(TransactionDetailDto.class))).thenReturn(TransactionDetailDto.builder()
                .id(1L)
                .transactionId(1L)
                .qty(5)
                .build());
        ResponseEntity<Object> responseEntity = transactionDetailService.getTransactionDetailById(1L);

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.SUCCESS, Objects.requireNonNull(apiResponse).getMessage());
    }

    @Test
    void getTransactionDetailByIdNotFound_Test() {
        when(transactionDetailRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Object> responseEntity = transactionDetailService.getTransactionDetailById(1L);

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    void getTransactionDetailByIdException_Test() {
        when(transactionDetailRepository.findById(anyLong())).thenThrow(NullPointerException.class);
        assertThrows(Exception.class, () -> transactionDetailService.getTransactionDetailById(1L));
    }

    @Test
    void deleteTransactionDetailSuccess_Test() {
        when(transactionDetailRepository.findById(anyLong())).thenReturn(Optional.of(TransactionDetailDao.builder()
                .id(1L)
                .build()));
        doNothing().when(transactionDetailRepository).delete(any());

        ResponseEntity<Object> responseEntity = transactionDetailService.deleteTransactionDetail(1L);

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.SUCCESS, Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    void deleteTransactionDetailNotFound_Test() {
        when(transactionDetailRepository.findById(anyLong())).thenReturn(Optional.empty());
        ResponseEntity<Object> responseEntity = transactionDetailService.deleteTransactionDetail(1L);

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    void deleteTransactionDetailException_Test() {
        when(transactionDetailRepository.findById(anyLong())).thenThrow(NullPointerException.class);
        assertThrows(Exception.class, () -> transactionDetailService.deleteTransactionDetail(1L));
    }

    @Test
    void updateTransactionDetailSuccess_Test() {
        TransactionDetailDao transactionDetailDao = TransactionDetailDao.builder()
                .id(1L)
                .build();

        TransactionDetailDto transactionDetailDto = TransactionDetailDto.builder()
                .id(1L)
                .transactionId(1L)
                .qty(5)
                .build();

        when(transactionDetailRepository.findById(anyLong())).thenReturn(Optional.of(transactionDetailDao));
        when(modelMapper.map(any(), eq(TransactionDetailDao.class))).thenReturn(transactionDetailDao);
        when(modelMapper.map(any(), eq(TransactionDetailDto.class))).thenReturn(transactionDetailDto);


        ResponseEntity<Object> responseEntity = transactionDetailService.updateTransactionDetail(anyLong(), TransactionDetailDto.builder()
                .id(1L)
                .transactionId(1L)
                .qty(5)
                .build());


        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        TransactionDetailDto transactionDto1 = (TransactionDetailDto) Objects.requireNonNull(apiResponse).getData();

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.SUCCESS, Objects.requireNonNull(apiResponse).getMessage());
        assertEquals(transactionDetailDto.getQty(), transactionDto1.getQty());
    }

    @Test
    void updateTransactionDetailNotFound_Test() {
        when(transactionDetailRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Object> responseEntity = transactionDetailService.updateTransactionDetail(anyLong(), TransactionDetailDto.builder()
                .id(1L)
                .transactionId(1L)
                .qty(5)
                .build());

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    void updateTransactionDetailException_Test() {
        when(transactionDetailRepository.findById(anyLong())).thenThrow(NullPointerException.class);
        assertThrows(Exception.class, () -> transactionDetailService.updateTransactionDetail(anyLong(), TransactionDetailDto.builder()
                .id(1L)
                .transactionId(1L)
                .qty(5)
                .build()));
    }

}