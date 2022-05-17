package com.alterra.finalproject.controller;

import com.alterra.finalproject.domain.dto.AuthorDto;
import com.alterra.finalproject.domain.dto.BookDto;
import com.alterra.finalproject.service.BookService;
import com.alterra.finalproject.service.RestConsumerService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
@RequestMapping(value = "/v1/book", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "Book", value = "Book" )
public class BookController {


    @Autowired
    private BookService bookService;

    @Autowired
    private RestConsumerService restConsumerService;

    @ApiOperation(value = "Get all books",  response = BookDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success get list book"),
    })
    @GetMapping(value = "")
    public ResponseEntity<Object> getAll() {
        return bookService.getAllBook();
    }

    @ApiOperation(value = "Add book by id",  response = BookDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success get book by id"),
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getById(@PathVariable(value = "id") Long id){
        return bookService.getBookById(id);
    }


//    @GetMapping(value = "/search/{title}")
//    public ResponseEntity<Object> searchBookByTitle(@PathVariable(value = "title") String title){
//        return bookService.searchBookByTitle(title);
//    }


    @GetMapping(value = "/search")
    public ResponseEntity<Object> searchBookByTitle(@RequestParam(value = "title", required = false) String title){
        return bookService.searchBookByTitle(title);
    }

    @GetMapping(value = "/category/{categoryName}")
    public ResponseEntity<Object> searchBookByCategoryName(@PathVariable(value = "categoryName") String categoryName){
        return bookService.getBookByCategoryName(categoryName);
    }

    @GetMapping(value = "/find/{title}")
    public ResponseEntity<Object> findBookByTitle(@PathVariable(value = "title") String title){
        return bookService.getBookByTitle(title);
    }

    @GetMapping(value = "/rest/{title}")
    public ResponseEntity<Object> searchBookRestConsumer(@PathVariable(value = "title") String title) {
        String a = title;
        return restConsumerService.getBookRestConsumer(a);
    }


    @ApiOperation(value = "Add new book",  response = BookDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success add new book"),
    })
    @PostMapping(value = "")
    public ResponseEntity<Object> addBook(@RequestBody BookDto request) {
        try{
            return bookService.addBook(request);
        } catch (Exception e) {
            throw e;
        }
    }

    @ApiOperation(value = "Delete book",  response = BookDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success delete book"),
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable(value = "id") Long id) {
        return bookService.deleteBook(id);
    }

    @ApiOperation(value = "Update book",  response = BookDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success update book"),
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateBook(@PathVariable(value = "id") Long id, @RequestBody BookDto request) {
           return bookService.updateBook(id, request);
    }

}
