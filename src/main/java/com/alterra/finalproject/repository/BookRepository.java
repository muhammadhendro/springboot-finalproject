package com.alterra.finalproject.repository;

import com.alterra.finalproject.domain.dao.BookDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookDao, Long> {


//    List<BookDao> findAllByCategoryId(Long categoryId);
//
//    List<BookDao> findAllByCategoryName(String categoryName);
//

    @Query(value = "SELECT b FROM BookDao b WHERE b.title LIKE %:title%")
    List<BookDao> searchByTitleLike(@Param("title") String title);
}
