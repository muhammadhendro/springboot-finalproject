package com.alterra.finalproject.domain.dao;


import com.alterra.finalproject.constant.AppConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Table(name = "M_BOOK_ORDER")
public class TransactionDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_date")
    @JsonFormat( pattern = "dd-MM-yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDateTime orderDate;

    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerDao customer;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private BookDao book;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    private PaymentDao payment;



    @PrePersist
    void onCreate() {
        this.orderDate = LocalDateTime.now();

    }
}
