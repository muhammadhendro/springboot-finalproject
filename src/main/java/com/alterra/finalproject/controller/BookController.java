package com.alterra.finalproject.controller;

import com.alterra.finalproject.domain.dto.BookDto;
import com.alterra.finalproject.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/v1/book", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class BookController {


    @Autowired
    private BookService bookService;

    @GetMapping(value = "")
    public ResponseEntity<Object> getAll() {
        return bookService.getAllBook();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getById(@PathVariable(value = "id") Long id){
        return bookService.getBookById(id);
    }

    @PostMapping(value = "")

    public ResponseEntity<Object> addBook(@RequestBody BookDto request) {
        try{
            return bookService.addBook(request);
        } catch (Exception e) {
            throw e;
        }

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable(value = "id") Long id) {
        return bookService.deleteBook(id);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateBook(@PathVariable(value = "id") Long id, @RequestBody BookDto request) {
           return bookService.updateBook(id, request);
    }

}
