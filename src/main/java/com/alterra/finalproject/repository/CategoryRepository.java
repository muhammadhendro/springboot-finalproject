package com.alterra.finalproject.repository;

import com.alterra.finalproject.domain.dao.CategoryDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryDao, Long> {
}
