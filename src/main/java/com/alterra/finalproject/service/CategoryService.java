package com.alterra.finalproject.service;

import com.alterra.finalproject.constant.AppConstant;
import com.alterra.finalproject.domain.dao.AuthorDao;
import com.alterra.finalproject.domain.dao.BookDao;
import com.alterra.finalproject.domain.dao.CategoryDao;
import com.alterra.finalproject.domain.dao.CustomerDao;
import com.alterra.finalproject.domain.dto.BookDto;
import com.alterra.finalproject.domain.dto.CategoryDto;
import com.alterra.finalproject.domain.dto.CustomerDto;
import com.alterra.finalproject.repository.CategoryRepository;
import com.alterra.finalproject.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private ModelMapper mapper;

    public ResponseEntity<Object> getAllCategory() {
        log.info("Executing get all category.");
        try{
            List<CategoryDao> daoList = categoryRepository.findAll();
            return ResponseUtil.build(AppConstant.Message.SUCCESS, daoList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Happened error when get all category. Error: {}", e.getMessage());
            log.trace("Get error when get all category. ", e);
            throw e;
        }  }

    public ResponseEntity<Object> getCategoryById(Long id) {
        log.info("Executing get category by id: {} ", id);
        try {
            Optional<CategoryDao> categoryDao = categoryRepository.findById(id);
            if(categoryDao.isEmpty()) {
                log.info("category id: {} not found", id);
                return ResponseUtil.build(AppConstant.Message.NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }
            log.info("Executing get category by id success");
            return ResponseUtil.build(AppConstant.Message.SUCCESS, categoryDao, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Happened error when get category by id. Error: {}", e.getMessage());
            log.trace("Get error when get category by id. ", e);
            throw e;
        }
    }

    public ResponseEntity<Object> addCategory(CategoryDto request) {

        log.info("Executing add category with request: {}", request);
        try{
            CategoryDao categoryDao = mapper.map(request, CategoryDao.class);
            categoryDao = categoryRepository.save(categoryDao);
            log.info("Executing add category success");
            return ResponseUtil.build(AppConstant.Message.SUCCESS, mapper.map(categoryDao, CategoryDto.class), HttpStatus.OK);

        } catch (Exception e) {
            log.error("Happened error when add category. Error: {}", e.getMessage());
            log.trace("Get error when add category. ", e);
            throw e;   } }

    public ResponseEntity<Object> deleteCategory(Long id) {
        log.info("Executing delete category id: {}", id);
        try{
            Optional<CategoryDao> categoryDao = categoryRepository.findById(id);
            if(categoryDao.isEmpty()) {
                log.info("category {} not found", id);
                return ResponseUtil.build(AppConstant.Message.NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }
            categoryRepository.deleteById(id);
            log.info("Executing delete category success");
            return ResponseUtil.build(AppConstant.Message.SUCCESS, null, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Happened error when delete category. Error: {}", e.getMessage());
            log.trace("Get error when delete category. ", e);
            throw e;
        }   }

    public ResponseEntity<Object> updateCategory(Long id, CategoryDto request) {
        log.info("Executing update category with request: {}", request);
        try {
            Optional<CategoryDao> categoryDao = categoryRepository.findById(id);
            if(categoryDao.isEmpty()) {
                log.info("category {} not found", id);
                return ResponseUtil.build(AppConstant.Message.NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }
            categoryDao.ifPresent(res -> {
                res.setCategoryName(request.getCategoryName());
                categoryRepository.save(res);
            });
            log.info("Executing update category success");
            return ResponseUtil.build(AppConstant.Message.SUCCESS, mapper.map(categoryDao, CategoryDto.class), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Happened error when update category. Error: {}", e.getMessage());
            log.trace("Get error when update category. ", e);
            throw e;
        }
    }
}
