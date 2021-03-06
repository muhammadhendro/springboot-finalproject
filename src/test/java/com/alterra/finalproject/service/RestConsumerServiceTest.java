package com.alterra.finalproject.service;

import com.alterra.finalproject.constant.AppConstant;
import com.alterra.finalproject.controller.BookController;
import com.alterra.finalproject.domain.common.ApiResponse;
import com.alterra.finalproject.domain.common.RestTemplateResponse;
import com.alterra.finalproject.domain.dto.BookDto;
import com.alterra.finalproject.domain.dto.RestTemplateDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = RestConsumerService.class)
class RestConsumerServiceTest {

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private RestConsumerService restConsumerService;



    @Test
    void getBookRestConsumerSuccess_Test()  {
RestTemplate restTemplate = new RestTemplate();

        RestTemplateResponse<Object> restTemplateResponse = RestTemplateResponse.builder()
                .error("0").build();
        RestTemplateDto restTemplateDto = RestTemplateDto.builder()
                .title("java").build();

//        when(modelMapper.map(any(), eq(RestTemplateResponse.class))).thenReturn(restTemplateResponse);
//        when(modelMapper.map(any(), eq(RestTemplateDto.class))).thenReturn(restTemplateDto);

//        when(objectMapper.readValue(anyString(), eq(RestTemplateResponse.class))).thenReturn(restTemplateResponse);
//        when(objectMapper.readValue(anyString(), eq(RestTemplateDto.class))).thenReturn(restTemplateDto);
        // when(restTemplate.getForObject("https://api.itbook.store/1.0/search/java", RestTemplateResponse.class)).thenReturn(restTemplateResponse);
         // RestTemplateResponse response = restTemplate.getForObject("https://api.itbook.store/1.0/search/java", RestTemplateResponse.class);

       ResponseEntity<RestTemplateResponse> response = restTemplate.getForEntity("https://api.itbook.store/1.0/search/java", RestTemplateResponse.class);
       RestTemplateResponse response1 = response.getBody();
       // log.info("responseEntity: {}", responseEntity);
           List<RestTemplateDto> restTemplateDto1 = (List<RestTemplateDto>) response1.getBooks();

       assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
//        assertEquals("1", Objects.requireNonNull(apiResponse).getPage());
//        assertEquals(10, restTemplateDtos.size());

    }

}