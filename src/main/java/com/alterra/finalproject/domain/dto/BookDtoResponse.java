package com.alterra.finalproject.domain.dto;

import com.alterra.finalproject.domain.dao.AuthorDao;
import com.alterra.finalproject.domain.dao.CategoryDao;
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
public class BookDtoResponse implements Serializable {

    private static final long serialVersionUID = -7087356557915022962L;

    private Long id;

    private String title;

    private String description;

    private AuthorDao author;

    private CategoryDao category;

    private String publishDate;

    private Integer price;
}
