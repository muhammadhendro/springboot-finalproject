package com.alterra.finalproject.repository;

import com.alterra.finalproject.domain.dao.TransactionDetailDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionDetailRepository  extends JpaRepository<TransactionDetailDao, Long> {
}
