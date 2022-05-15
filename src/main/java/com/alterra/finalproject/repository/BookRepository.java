package com.alterra.finalproject.repository;

import com.alterra.finalproject.domain.dao.BookDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookDao, Long> {

    List<BookDao> findBookDaoByCategoryCategoryName(String categoryName);
    
    @Query(value = "SELECT b FROM BookDao b WHERE upper(b.title) LIKE UPPER(CONCAT('%', :title, '%') ) ")
    List<BookDao> findAllByTitle(@Param("title") String title);

    //BookDao findAllByTitleContaining(String title);

    BookDao findByTitle(String title);

  //  BookDao findByCategoryName(String categoryName);

}
