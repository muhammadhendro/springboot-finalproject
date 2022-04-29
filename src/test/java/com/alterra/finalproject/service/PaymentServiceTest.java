package com.alterra.finalproject.service;

import com.alterra.finalproject.constant.AppConstant;
import com.alterra.finalproject.domain.common.ApiResponse;
import com.alterra.finalproject.domain.dao.AuthorDao;
import com.alterra.finalproject.domain.dao.PaymentDao;
import com.alterra.finalproject.domain.dto.AuthorDto;
import com.alterra.finalproject.domain.dto.PaymentDto;
import com.alterra.finalproject.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = PaymentService.class)
class PaymentServiceTest {

    @Autowired
    private PaymentService paymentService;

    @MockBean
    private PaymentRepository paymentRepository;

    @MockBean
    private ModelMapper modelMapper;

    @Test
    void addPaymentSuccess_Test() {

        PaymentDao paymentDao = PaymentDao.builder()
                .id(1L)
                .build();

        PaymentDto paymentDto = PaymentDto.builder()
                .paymentName("gopay")
                .build();

        when(modelMapper.map(any(), eq(PaymentDao.class))).thenReturn(paymentDao);
        when(modelMapper.map(any(), eq(PaymentDto.class))).thenReturn(paymentDto);
        when(paymentRepository.save(any())).thenReturn(paymentDao);

        ResponseEntity<Object> responseEntity = paymentService.addPayment(PaymentDto.builder()
                        .paymentName("gopay")
                .build());

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        PaymentDto paymentDto1 = (PaymentDto) Objects.requireNonNull(apiResponse).getData();

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.SUCCESS, Objects.requireNonNull(apiResponse).getMessage());
        assertEquals(paymentDto.getPaymentName(), paymentDto1.getPaymentName());

    }

    @Test
    void addPaymentException_Test() {
        when(paymentRepository.findById(anyLong())).thenReturn(Optional.of(PaymentDao.builder().paymentName("gopay").build()));
        doThrow(NullPointerException.class).when(paymentRepository).delete(any());
        assertThrows(Exception.class, () -> paymentService.addPayment(any()));
    }

    @Test
    void getAllPaymentSuccess_Test() {

        PaymentDao paymentDao = PaymentDao.builder()
                .id(1L)
                .build();
        when(paymentRepository.findAll()).thenReturn(List.of(paymentDao));
        when(modelMapper.map(any(), eq(PaymentDto.class))).thenReturn(PaymentDto.builder()
                        .paymentName("gopay")
                .build());
        ResponseEntity<Object> responseEntity = paymentService.getAllPayment();

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        List<PaymentDto> paymentDtos = (List<PaymentDto>) apiResponse.getData();

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.SUCCESS, Objects.requireNonNull(apiResponse).getMessage());
        assertEquals(1, paymentDtos.size());

    }

    @Test
    void getAllPaymentException_Test() {
        when(paymentRepository.findAll()).thenThrow(NullPointerException.class);
        assertThrows(Exception.class, () -> paymentService.getAllPayment());
    }

    @Test
    void deletePaymentSuccess_Test() {
        when(paymentRepository.findById(anyLong())).thenReturn(Optional.of(PaymentDao.builder()
                .id(1L)
                .paymentName("gopay")
                .build()));
        doNothing().when(paymentRepository).delete(any());

        ResponseEntity<Object> responseEntity = paymentService.deletePayment(1L);

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.SUCCESS, Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    void deletePaymentNotFound_Test() {
        when(paymentRepository.findById(anyLong())).thenReturn(Optional.empty());
        ResponseEntity<Object> responseEntity = paymentService.deletePayment(1L);

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    void deletePaymentException_Test() {
        when(paymentRepository.findById(anyLong())).thenThrow(NullPointerException.class);
        assertThrows(Exception.class, () -> paymentService.deletePayment(1L));
    }

    @Test
    void updatePaymentSuccess_Test() {
        PaymentDao paymentDao = PaymentDao.builder()
                .id(1L)
                .build();

        PaymentDto paymentDto = PaymentDto.builder()
                .paymentName("gopay")
                .build();

        when(paymentRepository.findById(anyLong())).thenReturn(Optional.of(paymentDao));
        when(modelMapper.map(any(), eq(PaymentDao.class))).thenReturn(paymentDao);
        when(modelMapper.map(any(), eq(PaymentDto.class))).thenReturn(paymentDto);


        ResponseEntity<Object> responseEntity = paymentService.updatePayment(anyLong(), PaymentDto.builder()
                        .paymentName("gopay")
                .build());


        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        PaymentDto paymentDto1 = (PaymentDto) Objects.requireNonNull(apiResponse).getData();

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.SUCCESS, Objects.requireNonNull(apiResponse).getMessage());
        assertEquals(paymentDto.getPaymentName(), paymentDto1.getPaymentName());
    }

    @Test
    void updatePaymentNotFound_Test() {
        when(paymentRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Object> responseEntity = paymentService.updatePayment(anyLong(), PaymentDto.builder()
                        .paymentName("gopay")
                .build());

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    void updatePaymentException_Test() {
        when(paymentRepository.findById(anyLong())).thenThrow(NullPointerException.class);
        assertThrows(Exception.class, () -> paymentService.updatePayment(anyLong(), PaymentDto.builder()
                        .paymentName("gopay")
                .build()));



    }
}