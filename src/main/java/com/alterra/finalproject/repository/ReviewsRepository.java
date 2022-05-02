package com.alterra.finalproject.repository;

import com.alterra.finalproject.domain.dao.ReviewsDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewsRepository extends JpaRepository<ReviewsDao, Long> {
}
