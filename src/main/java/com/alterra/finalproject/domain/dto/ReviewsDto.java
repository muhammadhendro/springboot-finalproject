package com.alterra.finalproject.domain.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
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
public class ReviewsDto implements Serializable {



    private static final long serialVersionUID = -4196690762013334443L;
    private Long id;

    private Long transactionId;


    @ApiModelProperty(notes = "Rating book", example = "1-100")
    private Integer rating;

    @ApiModelProperty(notes = "Review book", example = "Its good and cool")
    private String review;
}