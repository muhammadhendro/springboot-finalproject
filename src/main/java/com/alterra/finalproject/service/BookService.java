package com.alterra.finalproject.service;


import com.alterra.finalproject.constant.AppConstant;
import com.alterra.finalproject.domain.dao.BookDao;
import com.alterra.finalproject.domain.dao.CategoryDao;
import com.alterra.finalproject.domain.dto.BookDto;
import com.alterra.finalproject.repository.BookRepository;
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
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public ResponseEntity<Object> getAllBook() {
        List<BookDao> daoList = bookRepository.findAll();
        return ResponseUtil.build(AppConstant.Message.SUCCESS, daoList, HttpStatus.OK);
    }

    public ResponseEntity<Object> getBookById(Long id) {
        Optional<BookDao> bookDaoOptional = bookRepository.findById(id);
        if(bookDaoOptional.isEmpty()) {
            return ResponseUtil.build(AppConstant.Message.NOT_FOUND, null, HttpStatus.BAD_REQUEST);
        }
        return ResponseUtil.build(AppConstant.Message.SUCCESS, bookDaoOptional, HttpStatus.OK);

    }

    public ResponseEntity<Object> addBook(BookDto request) {

        Optional<CategoryDao> categoryDao = categoryRepository.findById(request.getCategoryId());

        BookDao bookDao = BookDao.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .description(request.getDescription())
                .category(categoryDao.orElse(null))
                .publishDate(request.getPublishDate())
                .price(request.getPrice())
                .build();
        bookRepository.save(bookDao);
        return ResponseUtil.build(AppConstant.Message.SUCCESS, bookDao, HttpStatus.OK);
    }

    public ResponseEntity<Object> deleteBook(Long id) {
        bookRepository.deleteById(id);
        return ResponseUtil.build(AppConstant.Message.SUCCESS, null, HttpStatus.OK);

    }

    public ResponseEntity<Object> updateBook(Long id, BookDto request) {
        Optional<BookDao>bookDaoOptional= bookRepository.findById(id);
        Optional<CategoryDao> categoryDao = categoryRepository.findById(request.getCategoryId());

        if(bookDaoOptional.isEmpty()) {
            return ResponseUtil.build(AppConstant.Message.NOT_FOUND, null, HttpStatus.BAD_REQUEST);
        }

        bookDaoOptional.ifPresent(res -> {
            res.setTitle(request.getTitle());
            res.setAuthor(request.getAuthor());
            res.setDescription(request.getDescription());
            res.setCategory(categoryDao.orElse(null));
            res.setPublishDate(request.getPublishDate());
            res.setPrice(request.getPrice());
            bookRepository.save(res);
        });
        return ResponseUtil.build(AppConstant.Message.SUCCESS, bookDaoOptional.get(), HttpStatus.OK);




    }


}
