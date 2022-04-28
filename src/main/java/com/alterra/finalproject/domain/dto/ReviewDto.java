package com.alterra.finalproject.domain.dto;


import com.alterra.finalproject.domain.dao.BookDao;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.Column;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ReviewDto implements Serializable {

    private static final long serialVersionUID = 2530147447600703396L;

    private Long id;

    private Long customerId;

    private Long bookId;

    private Integer rating;

    private String review;
}
