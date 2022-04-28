package com.alterra.finalproject.service;

import com.alterra.finalproject.constant.AppConstant;
import com.alterra.finalproject.domain.dao.AuthorDao;
import com.alterra.finalproject.domain.dao.CategoryDao;
import com.alterra.finalproject.domain.dao.CustomerDao;
import com.alterra.finalproject.domain.dto.AuthorDto;
import com.alterra.finalproject.domain.dto.CategoryDto;
import com.alterra.finalproject.domain.dto.CustomerDto;
import com.alterra.finalproject.repository.AuthorRepository;
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
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ModelMapper mapper;

    public ResponseEntity<Object> getAllAuthor() {
        log.info("Executing get all author.");
        try{
            List<AuthorDao> daoList = authorRepository.findAll();
            return ResponseUtil.build(AppConstant.Message.SUCCESS, daoList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Happened error when get all author. Error: {}", e.getMessage());
            log.trace("Get error when get all author. ", e);
            throw e;
        }  }


    public ResponseEntity<Object> addAuthor(AuthorDto request) {
        log.info("Executing add author with request: {}", request);
        try{
            AuthorDao authorDao = mapper.map(request, AuthorDao.class);
            authorDao = authorRepository.save(authorDao);
            log.info("Executing add author success");
            return ResponseUtil.build(AppConstant.Message.SUCCESS, mapper.map(authorDao, AuthorDto.class), HttpStatus.OK);

        } catch (Exception e) {
            log.error("Happened error when add author. Error: {}", e.getMessage());
            log.trace("Get error when add author. ", e);
            throw e;   } }


    public ResponseEntity<Object> deleteAuthor(Long id) {
        log.info("Executing delete author id: {}", id);
        try{
            Optional<AuthorDao> authorDao = authorRepository.findById(id);
            if(authorDao.isEmpty()) {
                log.info("author {} not found", id);
                return ResponseUtil.build(AppConstant.Message.NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }
            authorRepository.deleteById(id);
            log.info("Executing delete author success");
            return ResponseUtil.build(AppConstant.Message.SUCCESS, null, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Happened error when delete author. Error: {}", e.getMessage());
            log.trace("Get error when delete author. ", e);
            throw e;
        }   }

    public ResponseEntity<Object> updateAuthor(Long id, AuthorDto request) {
        log.info("Executing update author with request: {}", request);
        try {
            Optional<AuthorDao> authorDao= authorRepository.findById(id);
            if(authorDao.isEmpty()) {
                log.info("author {} not found", id);
                return ResponseUtil.build(AppConstant.Message.NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }
            authorDao.ifPresent(res -> {
                res.setName(request.getAuthorName());
                authorRepository.save(res);
            });
            log.info("Executing update author success");
            return ResponseUtil.build(AppConstant.Message.SUCCESS, mapper.map(authorDao, AuthorDto.class), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Happened error when update author. Error: {}", e.getMessage());
            log.trace("Get error when update author. ", e);
            throw e;
        }
    }
}
