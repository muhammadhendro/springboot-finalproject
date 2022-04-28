package com.alterra.finalproject.repository;

import com.alterra.finalproject.domain.dao.BookDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookDao, Long> {


    List<BookDao> findAllByCategoryId(Long categoryId);

    BookDao findByTitleContaining(String title);
}
