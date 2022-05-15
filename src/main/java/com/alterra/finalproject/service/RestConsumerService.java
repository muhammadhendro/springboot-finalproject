package com.alterra.finalproject.service;


import com.alterra.finalproject.constant.AppConstant;
import com.alterra.finalproject.domain.common.ApiResponse;
import com.alterra.finalproject.domain.common.RestTemplateResponse;
import com.alterra.finalproject.domain.dto.BookDto;
import com.alterra.finalproject.domain.dto.RestTemplateDto;
import com.alterra.finalproject.util.ResponseUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class RestConsumerService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${rest-consumer.url}")
    private String restConsumerUrl;

    @SneakyThrows
    public ResponseEntity<Object> getBookRestConsumer(String name) {
//        ObjectMapper mapper = new ObjectMapper();

        try {
            log.info("Executing search book restConsumer with name : {}",  name);
            log.info("Executing link: {}", String.format("%s/%s", restConsumerUrl, name));
            RestTemplateResponse restTemplateResponse =  restTemplate.getForObject(String.format("%s/%s", restConsumerUrl, name), RestTemplateResponse.class);
            log.info("restTemplateDto: {}",  restTemplateResponse);
            List<RestTemplateDto> restTemplateDto = (List<RestTemplateDto>) restTemplateResponse.getBooks();

            return ResponseUtil.build(AppConstant.Message.SUCCESS, restTemplateDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error when request API from rest", e);
            throw e;
        }
    }
}
