package com.alterra.finalproject.domain.dto;

import com.alterra.finalproject.domain.dao.AuthorDao;
import com.alterra.finalproject.domain.dao.CategoryDao;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookDto implements Serializable {

    private static final long serialVersionUID = -5607905544859605735L;

    private Long id;

    private String title;



    private String description;

    private Long authorId;

    private Long categoryId;

//    private AuthorDto author;
//
//    private CategoryDto category;

    private String publishDate;

    private Integer price;
}
