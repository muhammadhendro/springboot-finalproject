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
public class CustomerDto implements Serializable {

    private static final long serialVersionUID = -739280711757194732L;

    @ApiModelProperty(notes = "Full name customer", example = "Muhammad Hendro")
    private String customerName;

    @ApiModelProperty(notes = "Customer address", example = "Lampung")
    private String address;

}
