package com.alterra.finalproject.repository;

import com.alterra.finalproject.domain.dao.PaymentDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentDao, Long> {
}
