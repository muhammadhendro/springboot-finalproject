package com.alterra.finalproject.domain.dto;

import com.alterra.finalproject.domain.dao.BookDao;
import com.alterra.finalproject.domain.dao.CustomerDao;
import com.alterra.finalproject.domain.dao.PaymentDao;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TransactionDtoResponse implements Serializable {

    private static final long serialVersionUID = 5882251685212479272L;

    private Long id;

    private CustomerDao customer;

    private BookDtoResponse book;

    private PaymentDao payment;

    private LocalDateTime orderDate;

    private String status;
}
