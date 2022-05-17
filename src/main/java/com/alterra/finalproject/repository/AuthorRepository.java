package com.alterra.finalproject.repository;

import com.alterra.finalproject.domain.dao.AuthorDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorDao, Long> {
}
