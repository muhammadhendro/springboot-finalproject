package com.alterra.finalproject.controller;

import com.alterra.finalproject.constant.AppConstant;
import com.alterra.finalproject.domain.dao.AuthorDao;
import com.alterra.finalproject.domain.dto.AuthorDto;
import com.alterra.finalproject.domain.dto.CategoryDto;
import com.alterra.finalproject.service.AuthorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/author", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "Author", value = "Author" )
public class AuthorController {

    @Autowired
    private AuthorService authorService;


    @ApiOperation(value = "Get all authors",  response = AuthorDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success get list author"),
            @ApiResponse(code = 500, message = "Internal server error"),
    })
    @GetMapping(value = "")
    public ResponseEntity<Object> getAll() {
        return authorService.getAllAuthor();
    }

    @ApiOperation(value = "Add new author",  response = AuthorDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success add new author"),
    })
    @PostMapping(value = "")
    public ResponseEntity<Object> addAuthor(@RequestBody AuthorDto request) {
        return authorService.addAuthor(request);
    }

    @ApiOperation(value = "Delete author",  response = AuthorDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success delete author"),
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteAuthor(@PathVariable(value = "id") Long id) {
        return authorService.deleteAuthor(id);
    }

    @ApiOperation(value = "Update author",  response = AuthorDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success update author"),
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateAuthor(@PathVariable(value = "id") Long id, @RequestBody AuthorDto request) {
        return authorService.updateAuthor(id, request);
    }
}
