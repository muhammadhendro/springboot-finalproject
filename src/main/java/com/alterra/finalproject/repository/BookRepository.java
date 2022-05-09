package com.alterra.finalproject.repository;

import com.alterra.finalproject.domain.dao.BookDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookDao, Long> {

    List<BookDao> findAllByCategoryCategoryName(String categoryName);

    @Query(value = "SELECT b FROM BookDao b WHERE b.title like concat('%', ?1, '%')")
    List<BookDao> findByTitleContainingIgnoreCase(String title);

    BookDao findByTitle(String title);

  //  BookDao findByCategoryName(String categoryName);

}
