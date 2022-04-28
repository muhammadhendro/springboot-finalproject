package com.alterra.finalproject.domain.dao;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Table(name = "M_REVIEW")
public class ReviewDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerDao customer;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private BookDao book;



    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "review", nullable = false)
    private String review;

}
