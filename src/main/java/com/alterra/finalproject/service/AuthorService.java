package com.alterra.finalproject.service;

import com.alterra.finalproject.constant.AppConstant;
import com.alterra.finalproject.domain.dao.AuthorDao;
import com.alterra.finalproject.domain.dao.CategoryDao;
import com.alterra.finalproject.domain.dto.AuthorDto;
import com.alterra.finalproject.domain.dto.CategoryDto;
import com.alterra.finalproject.repository.AuthorRepository;
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
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public ResponseEntity<Object> getAllAuthor() {
        List<AuthorDao> daoList = authorRepository.findAll();
        return ResponseUtil.build(AppConstant.Message.SUCCESS, daoList, HttpStatus.OK);
    }


    public ResponseEntity<Object> addAuthor(AuthorDto request) {

        AuthorDao authorDao = AuthorDao.builder()
                .name(request.getAuthorName())
                .build();
        authorRepository.save(authorDao);
        return ResponseUtil.build(AppConstant.Message.SUCCESS, authorDao, HttpStatus.OK);
    }

    public ResponseEntity<Object> deleteAuthor(Long id) {
        authorRepository.deleteById(id);
        return ResponseUtil.build(AppConstant.Message.SUCCESS, null, HttpStatus.OK);

    }

    public ResponseEntity<Object> updateAuthor(Long id, AuthorDto request) {
        Optional<AuthorDao> authorDao= authorRepository.findById(id);

        if(authorDao.isEmpty()) {
            return ResponseUtil.build(AppConstant.Message.NOT_FOUND, null, HttpStatus.BAD_REQUEST);
        }

        authorDao.ifPresent(res -> {
            res.setName(request.getAuthorName());
            authorRepository.save(res);
        });
        return ResponseUtil.build(AppConstant.Message.SUCCESS, authorDao.get(), HttpStatus.OK);




    }
}
