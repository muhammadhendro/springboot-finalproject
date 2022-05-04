package com.alterra.finalproject.service;




import com.alterra.finalproject.constant.AppConstant;
import com.alterra.finalproject.domain.dao.AuthorDao;
import com.alterra.finalproject.domain.dao.BookDao;
import com.alterra.finalproject.domain.dao.CategoryDao;
import com.alterra.finalproject.domain.dto.BookDto;
import com.alterra.finalproject.repository.AuthorRepository;
import com.alterra.finalproject.repository.BookRepository;
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
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ModelMapper mapper;

    public ResponseEntity<Object> getAllBook() {
        log.info("Executing get all book.");
        try{
            List<BookDao> daoList = bookRepository.findAll();
            return ResponseUtil.build(AppConstant.Message.SUCCESS, daoList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Happened error when get all book. Error: {}", e.getMessage());
            log.trace("Get error when get all book. ", e);
            throw e;
        }
          }

    public ResponseEntity<Object> getBookById(Long id) {
        log.info("Executing get book by id: {} ", id);
        try {
            Optional<BookDao> bookDaoOptional = bookRepository.findById(id);
            if(bookDaoOptional.isEmpty()) {
                log.info("Book id: {} not found", id);
                return ResponseUtil.build(AppConstant.Message.NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }
            log.info("Executing get book by id success");
            return ResponseUtil.build(AppConstant.Message.SUCCESS, bookDaoOptional, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Happened error when get book by id. Error: {}", e.getMessage());
            log.trace("Get error when get book by id. ", e);
            throw e;
        }
    }

    public ResponseEntity<Object> searchBookByTitle(String title) {
        try {
            log.info("Executing search book by title: [{}]", title);
            List<BookDao> bookDao = bookRepository.searchByTitleLike(title);

            return ResponseUtil.build(AppConstant.Message.SUCCESS, mapper.map(bookDao, BookDto.class), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Happened error when search book by title. Error: {}", e.getMessage());
            log.trace("Get error when search book by title. ", e);
            throw e;
        }
    }

    public ResponseEntity<Object> addBook(BookDto request) {
        log.info("Executing add book with request: {}", request);
        try{
            log.info("Get category by id: {}", request.getCategoryId());
            Optional<CategoryDao> categoryDao = categoryRepository.findById(request.getCategoryId());
            if (categoryDao.isEmpty()) {
                log.info("Category [{}] not found", request.getCategoryId());
                return ResponseUtil.build(AppConstant.Message.NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }
            log.info("Get author by id: {}", request.getAuthorId());
            Optional<AuthorDao> authorDao = authorRepository.findById(request.getAuthorId());
            if (authorDao.isEmpty()) {
                log.info("Author [{}] not found", request.getAuthorId());
                return ResponseUtil.build(AppConstant.Message.NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }
            BookDao bookDao = mapper.map(request, BookDao.class);
            bookDao.setCategory(categoryDao.get());
            bookDao.setAuthor(authorDao.get());
            bookDao = bookRepository.save(bookDao);
            log.info("Executing add book success");
            return ResponseUtil.build(AppConstant.Message.SUCCESS, mapper.map(bookDao, BookDto.class), HttpStatus.OK);

        } catch (Exception e) {
            log.error("Happened error when add book. Error: {}", e.getMessage());
            log.trace("Get error when add book. ", e);
            throw e;   }
        }

    public ResponseEntity<Object> deleteBook(Long id) {
        log.info("Executing delete book id: {}", id);
        try{
            Optional<BookDao> bookDaoOptional =bookRepository.findById(id);
            if(bookDaoOptional.isEmpty()) {
                log.info("Book {} not found", id);
                return ResponseUtil.build(AppConstant.Message.NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }
            bookRepository.deleteById(id);
            log.info("Executing delete book success");
            return ResponseUtil.build(AppConstant.Message.SUCCESS, null, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Happened error when delete book. Error: {}", e.getMessage());
            log.trace("Get error when delete book. ", e);
            throw e;
        }

    }

    public ResponseEntity<Object> updateBook(Long id, BookDto request) {
        log.info("Executing update book with request: {}", request);
        try {
            Optional<BookDao>bookDaoOptional= bookRepository.findById(id);
            if(bookDaoOptional.isEmpty()) {
                log.info("Book {} not found", id);
                return ResponseUtil.build(AppConstant.Message.NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }
            bookDaoOptional.ifPresent(res -> {
                res.setTitle(request.getTitle());
                res.setDescription(request.getDescription());
                res.setPublishDate(request.getPublishDate());
                res.setPrice(request.getPrice());
                bookRepository.save(res);
            });
            log.info("Executing update book success");
            return ResponseUtil.build(AppConstant.Message.SUCCESS, mapper.map(bookDaoOptional, BookDto.class), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Happened error when update book. Error: {}", e.getMessage());
            log.trace("Get error when update book. ", e);
            throw e;
        }
    }


}
