package com.alterra.finalproject.service;

import com.alterra.finalproject.constant.AppConstant;
import com.alterra.finalproject.domain.common.ApiResponse;
import com.alterra.finalproject.domain.dao.AuthorDao;
import com.alterra.finalproject.domain.dao.BookDao;
import com.alterra.finalproject.domain.dao.CategoryDao;
import com.alterra.finalproject.domain.dto.BookDto;
import com.alterra.finalproject.domain.dto.CategoryDto;
import com.alterra.finalproject.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CategoryService.class)
class CategoryServiceTest {

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private CategoryService categoryService;

    @Test
    void addCategorySuccess_Test() {
        CategoryDao categoryDao = CategoryDao.builder()
                .id(1L)
                .build();

        CategoryDto categoryDto = CategoryDto.builder()
                .id(1L)
                .categoryName("buku")
                .build();

        when(modelMapper.map(any(), eq(CategoryDao.class))).thenReturn(categoryDao);
        when(modelMapper.map(any(), eq(CategoryDto.class))).thenReturn(categoryDto);
        when(categoryRepository.save(any())).thenReturn(categoryDao);

        ResponseEntity<Object> responseEntity = categoryService.addCategory(CategoryDto.builder()
                        .categoryName("buku")
                .build());

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        CategoryDto productDtoResponse = (CategoryDto) Objects.requireNonNull(apiResponse).getData();

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.SUCCESS, Objects.requireNonNull(apiResponse).getMessage());
        assertEquals(categoryDto.getCategoryName(), productDtoResponse.getCategoryName());
    }

    @Test
    void addCategoryException_Test() {

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(CategoryDao.builder().categoryName("hendro").build()));
        doThrow(NullPointerException.class).when(categoryRepository).delete(any());
        assertThrows(Exception.class, () -> categoryService.addCategory(any()));

    }

    @Test
    void getAllCategorySuccess_Test() {

        CategoryDao categoryDao = CategoryDao.builder()
                .id(1L)
                .categoryName("buku")
                .build();

        when(categoryRepository.findAll()).thenReturn(List.of(categoryDao));
        when(modelMapper.map(any(), eq(CategoryDto.class))).thenReturn(CategoryDto.builder()
                .id(1L)
                .categoryName("buku")
                .build());
        ResponseEntity<Object> responseEntity = categoryService.getAllCategory();

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        List<CategoryDto> bookDtos = (List<CategoryDto>) apiResponse.getData();

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.SUCCESS, Objects.requireNonNull(apiResponse).getMessage());
        assertEquals(1, bookDtos.size());

    }

    @Test
    void getAllCategoryException_Test() {
        when(categoryRepository.findAll()).thenThrow(NullPointerException.class);
        assertThrows(Exception.class, () -> categoryService.getAllCategory());
    }

    @Test
    void getCategoryByIdSuccess_Test() {

        CategoryDao categoryDao = CategoryDao.builder()
                .id(1L)
                .categoryName("buku")
                .build();
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(categoryDao));
        when(modelMapper.map(any(), eq(CategoryDto.class))).thenReturn(CategoryDto.builder()
                .id(1L)
                        .categoryName("buku")
                .build());
        ResponseEntity<Object> responseEntity = categoryService.getCategoryById(1L);

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.SUCCESS, Objects.requireNonNull(apiResponse).getMessage());
    }

    @Test
    void getCategoryByIdNotFound_Test() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Object> responseEntity = categoryService.getCategoryById(1L);

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    void getCategoryByIdException_Test() {
        when(categoryRepository.findById(anyLong())).thenThrow(NullPointerException.class);
        assertThrows(Exception.class, () -> categoryService.getCategoryById(1L));
    }

    @Test
    void deleteBookSuccess_Test() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(CategoryDao.builder()
                .id(1L)
                        .categoryName("buku")
                .build()));
        doNothing().when(categoryRepository).delete(any());

        ResponseEntity<Object> responseEntity = categoryService.deleteCategory(1L);

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.SUCCESS, Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    void deleteCategoryNotFound_Test() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());
        ResponseEntity<Object> responseEntity = categoryService.deleteCategory(1L);

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    void deleteCategoryException_Test() {
        when(categoryRepository.findById(anyLong())).thenThrow(NullPointerException.class);
        assertThrows(Exception.class, () -> categoryService.deleteCategory(1L));
    }


    @Test
    void updateCategorySuccess_Test() {
        CategoryDao categoryDao = CategoryDao.builder()
                .id(1L)
                .build();

        CategoryDto categoryDto = CategoryDto.builder()
                .id(1L)
                .categoryName("buku")
                .build();

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(categoryDao));
        when(modelMapper.map(any(), eq(CategoryDao.class))).thenReturn(categoryDao);
        when(modelMapper.map(any(), eq(CategoryDto.class))).thenReturn(categoryDto);


        ResponseEntity<Object> responseEntity = categoryService.updateCategory(anyLong(), CategoryDto.builder()
                        .categoryName("buku")
                .build());


        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        CategoryDto categoryDto1 = (CategoryDto) Objects.requireNonNull(apiResponse).getData();

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.SUCCESS, Objects.requireNonNull(apiResponse).getMessage());
        assertEquals(categoryDto.getCategoryName(), categoryDto1.getCategoryName());
    }

    @Test
    void updateCategoryNotFound_Test() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Object> responseEntity = categoryService.updateCategory(anyLong(), CategoryDto.builder()
                        .categoryName("buku")
                .build());

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    void updateCategoryException_Test() {
        when(categoryRepository.findById(anyLong())).thenThrow(NullPointerException.class);
        assertThrows(Exception.class, () -> categoryService.updateCategory(anyLong(), CategoryDto.builder()
                        .categoryName("buku")
                .build()));



    }
}