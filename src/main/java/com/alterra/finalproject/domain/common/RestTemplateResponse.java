package com.alterra.finalproject.domain.common;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestTemplateResponse<T> implements Serializable {

    private static final long serialVersionUID = 1938019526660446048L;
    private String error;
    private String total;
    private String page;

    private T books;
}
