package com.alterra.finalproject.domain.dto;

import com.alterra.finalproject.domain.dao.TransactionDao;
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
public class TransactionDetailDtoResponse implements Serializable {

    private static final long serialVersionUID = 8121654691000163981L;

    private Long id;

    private TransactionDtoResponse transaction;

    private Integer qty;

    private Integer totalPrice;
}
