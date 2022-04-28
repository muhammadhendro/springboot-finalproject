package com.alterra.finalproject.repository;

import com.alterra.finalproject.domain.dao.BookDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookDao, Long> {
}
