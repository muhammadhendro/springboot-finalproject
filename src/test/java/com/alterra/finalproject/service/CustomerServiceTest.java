package com.alterra.finalproject.service;

import com.alterra.finalproject.constant.AppConstant;
import com.alterra.finalproject.domain.common.ApiResponse;
import com.alterra.finalproject.domain.dao.AuthorDao;
import com.alterra.finalproject.domain.dao.BookDao;
import com.alterra.finalproject.domain.dao.CategoryDao;
import com.alterra.finalproject.domain.dao.CustomerDao;
import com.alterra.finalproject.domain.dto.BookDto;
import com.alterra.finalproject.domain.dto.CustomerDto;
import com.alterra.finalproject.repository.CustomerRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CustomerService.class)
class CustomerServiceTest {

    @MockBean
    private CustomerRepository customerRepository;
    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private CustomerService customerService;

    @Test
    void addCustomerSuccess_Test() {


        CustomerDao customerDao = CustomerDao.builder()
                .id(1L)
                .build();

        CustomerDto customerDto = CustomerDto.builder()
                .customerName("hendro")
                .build();


        when(modelMapper.map(any(), eq(CustomerDao.class))).thenReturn(customerDao);
        when(modelMapper.map(any(), eq(CustomerDto.class))).thenReturn(customerDto);
        when(customerRepository.save(any())).thenReturn(customerDao);

        ResponseEntity<Object> responseEntity = customerService.addCustomer(CustomerDto.builder()
                        .customerName("hendro")
                .build());

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        CustomerDto customerDto1 = (CustomerDto) Objects.requireNonNull(apiResponse).getData();

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.SUCCESS, Objects.requireNonNull(apiResponse).getMessage());
        assertEquals(customerDto.getCustomerName(), customerDto1.getCustomerName());

    }

    @Test
    void addCustomerException_Test() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(CustomerDao.builder().customerName("hendro").build()));
        doThrow(NullPointerException.class).when(customerRepository).delete(any());
        assertThrows(Exception.class, () -> customerService.addCustomer(any()));
    }

    @Test
    void getAllCustomerSuccess_Test() {

        CustomerDao customerDao = CustomerDao.builder()
                .customerName("hendro")
                .build();
        when(customerRepository.findAll()).thenReturn(List.of(customerDao));
        when(modelMapper.map(any(), eq(CustomerDto.class))).thenReturn(CustomerDto.builder()
                        .customerName("hendro")
                .build());
        ResponseEntity<Object> responseEntity = customerService.getAllCustomer();

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        List<CustomerDto> customerDtos = (List<CustomerDto>) apiResponse.getData();

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.SUCCESS, Objects.requireNonNull(apiResponse).getMessage());
        assertEquals(1, customerDtos.size());

    }

    @Test
    void getAllCustomerException_Test() {
        when(customerRepository.findAll()).thenThrow(NullPointerException.class);
        assertThrows(Exception.class, () -> customerService.getAllCustomer());
    }

    @Test
    void getCustomerByIdSuccess_Test() {

        CustomerDao customerDao = CustomerDao.builder()
                .customerName("hendro")
                .build();
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customerDao));
        when(modelMapper.map(any(), eq(CustomerDto.class))).thenReturn(CustomerDto.builder()
                        .customerName("hendro")
                .build());
        ResponseEntity<Object> responseEntity = customerService.getCustomerById(1L);

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.SUCCESS, Objects.requireNonNull(apiResponse).getMessage());


    }

    @Test
    void getCustomerByIdNotFound_Test() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Object> responseEntity = customerService.getCustomerById(1L);

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    void getCustomerByIdException_Test() {
        when(customerRepository.findById(anyLong())).thenThrow(NullPointerException.class);
        assertThrows(Exception.class, () -> customerService.getCustomerById(1L));
    }

    @Test
    void deleteCustomerSuccess_Test() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(CustomerDao.builder()
                        .customerName("hendro")
                .build()));
        doNothing().when(customerRepository).delete(any());

        ResponseEntity<Object> responseEntity = customerService.deleteCustomer(1L);

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.SUCCESS, Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    void deleteCustomerNotFound_Test() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());
        ResponseEntity<Object> responseEntity = customerService.deleteCustomer(1L);

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    void deleteCustomerException_Test() {
        when(customerRepository.findById(anyLong())).thenThrow(NullPointerException.class);
        assertThrows(Exception.class, () -> customerService.deleteCustomer(1L));
    }

    @Test
    void updateCustomerSuccess_Test() {
        CustomerDao customerDao = CustomerDao.builder()
                .id(1L)
                .build();

        CustomerDto customerDto = CustomerDto.builder()
                .customerName("hendro")
                .build();

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customerDao));
        when(modelMapper.map(any(), eq(CustomerDao.class))).thenReturn(customerDao);
        when(modelMapper.map(any(), eq(CustomerDto.class))).thenReturn(customerDto);


        ResponseEntity<Object> responseEntity = customerService.updateCustomer(anyLong(), CustomerDto.builder()
                        .customerName("hendro")
                .build());


        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        CustomerDto customerDto1 = (CustomerDto) Objects.requireNonNull(apiResponse).getData();

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.SUCCESS, Objects.requireNonNull(apiResponse).getMessage());
        assertEquals(customerDto.getCustomerName(), customerDto1.getCustomerName());


    }

    @Test
    void updateCustomerNotFound_Test() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Object> responseEntity = customerService.updateCustomer(anyLong(), CustomerDto.builder()
                        .customerName("hendro")
                .build());

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    void updateCustomerException_Test() {
        when(customerRepository.findById(anyLong())).thenThrow(NullPointerException.class);
        assertThrows(Exception.class, () -> customerService.updateCustomer(anyLong(), CustomerDto.builder()
                        .customerName("hendro")
                .build()));



    }
}