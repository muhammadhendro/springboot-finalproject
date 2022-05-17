package com.alterra.finalproject.controller;

import com.alterra.finalproject.domain.dto.AuthorDto;
import com.alterra.finalproject.domain.dto.BookDto;
import com.alterra.finalproject.domain.dto.CategoryDto;
import com.alterra.finalproject.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/category", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "Category", value = "Category" )
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "Get all category",  response = CategoryDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success get list category"),
    })
    @GetMapping(value = "")
    public ResponseEntity<Object> getAll() {
        return categoryService.getAllCategory();
    }


    @ApiOperation(value = "Add new category",  response = CategoryDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success add new category"),
    })
    @PostMapping(value = "")
    public ResponseEntity<Object> addCategory(@RequestBody CategoryDto request) {
        return categoryService.addCategory(request);
    }

    @ApiOperation(value = "Delete category",  response = CategoryDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success delete category"),
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteCategory(@PathVariable(value = "id") Long id) {
        return categoryService.deleteCategory(id);
    }

    @ApiOperation(value = "Update category",  response = CategoryDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success update category"),
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateCategory(@PathVariable(value = "id") Long id, @RequestBody CategoryDto request) {
        return categoryService.updateCategory(id, request);
    }
}
