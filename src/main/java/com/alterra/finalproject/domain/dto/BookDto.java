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
public class BookDto implements Serializable {

    private static final long serialVersionUID = -5607905544859605735L;

    private Long id;

    private String title;

    private String author;

    private String description;

    private Long categoryId;

    private String publishDate;

    private Integer price;
}
