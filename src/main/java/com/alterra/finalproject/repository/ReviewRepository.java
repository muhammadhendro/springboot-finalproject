package com.alterra.finalproject.repository;

import com.alterra.finalproject.domain.dao.ReviewDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewDao, Long> {
}
