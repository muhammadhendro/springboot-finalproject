package com.alterra.finalproject.domain.dto;

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
public class RestTemplateDto implements Serializable {

    private static final long serialVersionUID = -4632550132906063976L;

    private String title;
    private String subtitle;
    private String isbn13;
    private String price;
    private String image;
    private String url;

}
