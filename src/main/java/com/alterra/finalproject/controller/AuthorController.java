package com.alterra.finalproject.controller;

import com.alterra.finalproject.domain.dto.AuthorDto;
import com.alterra.finalproject.domain.dto.CategoryDto;
import com.alterra.finalproject.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/author", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping(value = "")
    public ResponseEntity<Object> getAll() {
        return authorService.getAllAuthor();
    }



    @PostMapping(value = "")
    public ResponseEntity<Object> addAuthor(@RequestBody AuthorDto request) {
        return authorService.addAuthor(request);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteAuthor(@PathVariable(value = "id") Long id) {
        return authorService.deleteAuthor(id);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateAuthor(@PathVariable(value = "id") Long id, @RequestBody AuthorDto request) {
        return authorService.updateAuthor(id, request);
    }
}
