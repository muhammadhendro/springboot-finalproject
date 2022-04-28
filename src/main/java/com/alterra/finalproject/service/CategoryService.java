package com.alterra.finalproject.service;

import com.alterra.finalproject.constant.AppConstant;
import com.alterra.finalproject.domain.dao.BookDao;
import com.alterra.finalproject.domain.dao.CategoryDao;
import com.alterra.finalproject.domain.dto.BookDto;
import com.alterra.finalproject.domain.dto.CategoryDto;
import com.alterra.finalproject.repository.CategoryRepository;
import com.alterra.finalproject.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public ResponseEntity<Object> getAllCategory() {
        List<CategoryDao> daoList = categoryRepository.findAll();
        return ResponseUtil.build(AppConstant.Message.SUCCESS, daoList, HttpStatus.OK);
    }


    public ResponseEntity<Object> addCategory(CategoryDto request) {

        CategoryDao categoryDao = CategoryDao.builder()
                .categoryName(request.getCategoryName())
                .build();
        categoryRepository.save(categoryDao);
        return ResponseUtil.build(AppConstant.Message.SUCCESS, categoryDao, HttpStatus.OK);
    }

    public ResponseEntity<Object> deleteCategory(Long id) {
        categoryRepository.deleteById(id);
        return ResponseUtil.build(AppConstant.Message.SUCCESS, null, HttpStatus.OK);

    }

    public ResponseEntity<Object> updateCategory(Long id, CategoryDto request) {
        Optional<CategoryDao> categoryDao= categoryRepository.findById(id);

        if(categoryDao.isEmpty()) {
            return ResponseUtil.build(AppConstant.Message.NOT_FOUND, null, HttpStatus.BAD_REQUEST);
        }

        categoryDao.ifPresent(res -> {
            res.setCategoryName(request.getCategoryName());
            categoryRepository.save(res);
        });
        return ResponseUtil.build(AppConstant.Message.SUCCESS, categoryDao.get(), HttpStatus.OK);




    }
}
