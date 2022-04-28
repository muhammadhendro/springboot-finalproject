package com.alterra.finalproject.controller;

import com.alterra.finalproject.constant.AppConstant;
import com.alterra.finalproject.domain.dao.BookDao;
import com.alterra.finalproject.domain.dto.BookDto;
import com.alterra.finalproject.service.BookService;
import com.alterra.finalproject.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(SpringExtension.class) // JUnit 4 @RunWith(SpringRunner.class)
@SpringBootTest(classes = BookController.class)
@EnableWebMvc
@AutoConfigureMockMvc
class BookControllerTest {


    @MockBean
    private BookService bookService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    void addNewBookSuccess_Test() throws Exception{
        when(bookService.addBook(any()))
                .thenReturn(ResponseUtil.build(AppConstant.Message.SUCCESS, BookDto.builder()
                        .id(1L)
                        .build(), HttpStatus.OK));
        this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/book")
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{}"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void addNewBookException_Test() {
        when(bookService.addBook(any())).thenThrow(NullPointerException.class);

        Assertions.assertThrows(Exception.class, () -> this.mockMvc.perform(post("/v1/book")
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{}"))
                .andReturn());
    }

    @Test
     void updateBookSuccess_Test() throws Exception
    {
        mockMvc.perform( MockMvcRequestBuilders
                        .put("/v1/book/{id}", 1)
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
     void deleteBookSuccess_Test() throws Exception
    {
        mockMvc.perform( MockMvcRequestBuilders
                        .delete("/v1/book/{id}", 1)
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }


    @Test
    void getAllBookSuccess_Test() throws Exception
    {
        mockMvc.perform( MockMvcRequestBuilders
                        .get("/v1/book/" )
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
    @Test
    void getBookByIdSuccess_Test() throws Exception
    {
        mockMvc.perform( MockMvcRequestBuilders
                        .get("/v1/book/{id}", 1 )
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }





}