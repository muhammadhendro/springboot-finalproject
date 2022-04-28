package com.alterra.finalproject.controller;

import com.alterra.finalproject.domain.dto.BookDto;
import com.alterra.finalproject.domain.dto.CategoryDto;
import com.alterra.finalproject.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/category", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = "")
    public ResponseEntity<Object> getAll() {
        return categoryService.getAllCategory();
    }



    @PostMapping(value = "")
    public ResponseEntity<Object> addCategory(@RequestBody CategoryDto request) {
        return categoryService.addCategory(request);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteCategory(@PathVariable(value = "id") Long id) {
        return categoryService.deleteCategory(id);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateCategory(@PathVariable(value = "id") Long id, @RequestBody CategoryDto request) {
        return categoryService.updateCategory(id, request);
    }
}
