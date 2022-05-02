package com.alterra.finalproject.service;

import com.alterra.finalproject.constant.AppConstant;
import com.alterra.finalproject.domain.common.ApiResponse;
import com.alterra.finalproject.domain.dao.*;
import com.alterra.finalproject.domain.dto.BookDto;
import com.alterra.finalproject.domain.dto.TransactionDto;
import com.alterra.finalproject.repository.BookRepository;
import com.alterra.finalproject.repository.CustomerRepository;
import com.alterra.finalproject.repository.PaymentRepository;
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
@SpringBootTest(classes = TransactionService.class)
class TransactionServiceTest {

    @MockBean
    private TransactionRepository transactionRepository;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private PaymentRepository paymentRepository;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private TransactionService transactionService;

    @Test
    void addTransactionSuccess_Test() {
        CustomerDao customerDao = CustomerDao.builder()
                .id(1L)
                .build();

        BookDao bookDao = BookDao.builder()
                .id(1L)
                .build();

        PaymentDao paymentDao = PaymentDao.builder()
                .id(1L)
                .build();

        TransactionDao transactionDao = TransactionDao.builder()
                .id(1L)
                .build();

        TransactionDto transactionDto = TransactionDto.builder()
                .id(1L)
                .customerId(1L)
                .bookId(1L)
                .paymentId(1L)
                .status("paid")
                .build();

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customerDao));
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(bookDao));
        when(paymentRepository.findById(anyLong())).thenReturn(Optional.of(paymentDao));
        when(modelMapper.map(any(), eq(TransactionDao.class))).thenReturn(transactionDao);
        when(modelMapper.map(any(), eq(TransactionDto.class))).thenReturn(transactionDto);
        when(transactionRepository.save(any())).thenReturn(transactionDao);

        ResponseEntity<Object> responseEntity = transactionService.addTransaction(TransactionDto.builder()
                .id(1L)
                .customerId(1L)
                .bookId(1L)
                .paymentId(1L)
                .status("paid")
                .build());

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        TransactionDto transactionDto1 = (TransactionDto) Objects.requireNonNull(apiResponse).getData();

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.SUCCESS, Objects.requireNonNull(apiResponse).getMessage());
        assertEquals(transactionDto.getStatus(), transactionDto1.getStatus());
        assertEquals(transactionDto.getCustomerId(), transactionDto1.getCustomerId());

    }

    @Test
    void addTransactionCustomerIsEmpty_Test() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Object> responseEntity = transactionService.addTransaction(TransactionDto.builder()
                .id(1L)
                .customerId(1L)
                .bookId(1L)
                .paymentId(1L)
                .status("paid")
                .build());

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    void addTransactionBookIsEmpty_Test() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(CustomerDao.builder()
                .id(1L)
                .build()));
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Object> responseEntity = transactionService.addTransaction(TransactionDto.builder()
                .id(1L)
                .customerId(1L)
                .bookId(1L)
                .paymentId(1L)
                .status("paid")
                .build());

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    void addTransactionPaymentIsEmpty_Test() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(CustomerDao.builder()
                .id(1L)
                .build()));
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(BookDao.builder()
                .id(1L)
                .build()));
        when(paymentRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Object> responseEntity = transactionService.addTransaction(TransactionDto.builder()
                .id(1L)
                .customerId(1L)
                .bookId(1L)
                .paymentId(1L)
                .status("paid")
                .build());

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    void addTransactionException_Test() {
        when(customerRepository.findById(anyLong())).thenThrow(NullPointerException.class);
        assertThrows(Exception.class, () -> transactionService.addTransaction(TransactionDto.builder()
                .id(1L)
                .customerId(1L)
                .bookId(1L)
                .paymentId(1L)
                .status("paid")
                .build()));
    }

    @Test
    void getAllTransactionSuccess_Test() {

        TransactionDao transactionDao = TransactionDao.builder()
                .id(1L)
                .status("paid")
                .build();
        when(transactionRepository.findAll()).thenReturn(List.of(transactionDao));
        when(modelMapper.map(any(), eq(TransactionDto.class))).thenReturn(TransactionDto.builder()
                .id(1L)
                .status("paid")
                .build());
        ResponseEntity<Object> responseEntity = transactionService.getAllTransaction();

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        List<TransactionDto> transactionDtos = (List<TransactionDto>) apiResponse.getData();

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.SUCCESS, Objects.requireNonNull(apiResponse).getMessage());
        assertEquals(1, transactionDtos.size());

    }

    @Test
    void getAllTransactionException_Test() {
        when(transactionRepository.findAll()).thenThrow(NullPointerException.class);
        assertThrows(Exception.class, () -> transactionService.getAllTransaction());
    }

    @Test
    void getTranscationByIdSuccess_Test() {

        TransactionDao transactionDao = TransactionDao.builder()
                .id(1L)
                .status("paid")
                .build();
        when(transactionRepository.findById(anyLong())).thenReturn(Optional.of(transactionDao));
        when(modelMapper.map(any(), eq(TransactionDto.class))).thenReturn(TransactionDto.builder()
                .id(1L)
                .status("paid")
                .build());
        ResponseEntity<Object> responseEntity = transactionService.getTransactionById(1L);

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.SUCCESS, Objects.requireNonNull(apiResponse).getMessage());
    }

    @Test
    void getTransactionByIdNotFound_Test() {
        when(transactionRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Object> responseEntity = transactionService.getTransactionById(1L);

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    void getTransactionByIdException_Test() {
        when(transactionRepository.findById(anyLong())).thenThrow(NullPointerException.class);
        assertThrows(Exception.class, () -> transactionService.getTransactionById(1L));
    }

    @Test
    void deleteTransactionSuccess_Test() {
        when(transactionRepository.findById(anyLong())).thenReturn(Optional.of(TransactionDao.builder()
                .id(1L)
                .status("paid")
                .build()));
        doNothing().when(transactionRepository).delete(any());

        ResponseEntity<Object> responseEntity = transactionService.deleteTransaction(1L);

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.SUCCESS, Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    void deleteTransactionNotFound_Test() {
        when(transactionRepository.findById(anyLong())).thenReturn(Optional.empty());
        ResponseEntity<Object> responseEntity = transactionService.deleteTransaction(1L);

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    void deleteTransactionException_Test() {
        when(transactionRepository.findById(anyLong())).thenThrow(NullPointerException.class);
        assertThrows(Exception.class, () -> transactionService.deleteTransaction(1L));
    }

    @Test
    void updateTransactionSuccess_Test() {
        TransactionDao transactionDao = TransactionDao.builder()
                .id(1L)
                .build();

        TransactionDto transactionDto = TransactionDto.builder()
                .id(1L)
                .customerId(1L)
                .bookId(1L)
                .paymentId(1L)
                .status("paid")
                .build();

        when(transactionRepository.findById(anyLong())).thenReturn(Optional.of(transactionDao));
        when(modelMapper.map(any(), eq(TransactionDao.class))).thenReturn(transactionDao);
        when(modelMapper.map(any(), eq(TransactionDto.class))).thenReturn(transactionDto);


        ResponseEntity<Object> responseEntity = transactionService.updateTransaction(anyLong(), TransactionDto.builder()
                .id(1L)
                .customerId(1L)
                .bookId(1L)
                .paymentId(1L)
                .status("paid")
                .build());


        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        TransactionDto transactionDto1 = (TransactionDto) Objects.requireNonNull(apiResponse).getData();

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.SUCCESS, Objects.requireNonNull(apiResponse).getMessage());
        assertEquals(transactionDto.getStatus(), transactionDto1.getStatus());
    }

    @Test
    void updateTransactionNotFound_Test() {
        when(transactionRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Object> responseEntity = transactionService.updateTransaction(anyLong(), TransactionDto.builder()
                .id(1L)
                .customerId(1L)
                .bookId(1L)
                .paymentId(1L)
                .status("paid")
                .build());

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    void updateTransactionException_Test() {
        when(transactionRepository.findById(anyLong())).thenThrow(NullPointerException.class);
        assertThrows(Exception.class, () -> transactionService.updateTransaction(anyLong(), TransactionDto.builder()
                .id(1L)
                .customerId(1L)
                .bookId(1L)
                .paymentId(1L)
                .status("paid")
                .build()));
    }

}