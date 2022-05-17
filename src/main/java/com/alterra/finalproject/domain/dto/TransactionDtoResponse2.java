package com.alterra.finalproject.domain.dto;

import com.alterra.finalproject.domain.dao.CustomerDao;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TransactionDtoResponse2 implements Serializable {


    private static final long serialVersionUID = -1016549840575839112L;
    private Long id;

    private CustomerDao customer;

    private BookDtoResponse book;
}
