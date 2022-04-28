package com.alterra.finalproject.service;

import com.alterra.finalproject.constant.AppConstant;
import com.alterra.finalproject.domain.common.ApiResponse;
import com.alterra.finalproject.domain.dao.AuthorDao;
import com.alterra.finalproject.domain.dao.BookDao;
import com.alterra.finalproject.domain.dao.CategoryDao;
import com.alterra.finalproject.domain.dto.AuthorDto;
import com.alterra.finalproject.domain.dto.BookDto;
import com.alterra.finalproject.repository.AuthorRepository;
import com.alterra.finalproject.repository.BookRepository;
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

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AuthorService.class)
class AuthorServiceTest {


    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private AuthorService authorService;

    @Test
    void addAuthorSuccess_Test() {

        AuthorDao authorDao = AuthorDao.builder()
                .id(1L)
                .build();

        AuthorDto authorDto = AuthorDto.builder()
                .id(1L)
                .authorName("hendro")
                .build();

        when(modelMapper.map(any(), eq(AuthorDao.class))).thenReturn(authorDao);
        when(modelMapper.map(any(), eq(AuthorDto.class))).thenReturn(authorDto);
        when(authorRepository.save(any())).thenReturn(authorDao);

        ResponseEntity<Object> responseEntity = authorService.addAuthor(AuthorDto.builder()
                        .authorName("hendro")
                .build());

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        AuthorDto productDtoResponse = (AuthorDto) Objects.requireNonNull(apiResponse).getData();

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.SUCCESS, Objects.requireNonNull(apiResponse).getMessage());
        assertEquals(authorDto.getAuthorName(), productDtoResponse.getAuthorName());

    }

    @Test
    void addAuthorException_Test() {
        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(AuthorDao.builder().name("hendro").build()));
        doThrow(NullPointerException.class).when(authorRepository).delete(any());
        assertThrows(Exception.class, () -> authorService.addAuthor(any()));
    }

    @Test
    void getAllAuthorSuccess_Test() {

        AuthorDao authorDao = AuthorDao.builder()
                .id(1L)
                .name("hendro")
                .build();
        when(authorRepository.findAll()).thenReturn(List.of(authorDao));
        when(modelMapper.map(any(), eq(AuthorDto.class))).thenReturn(AuthorDto.builder()
                .id(1L)
                .authorName("hendro")
                .build());
        ResponseEntity<Object> responseEntity = authorService.getAllAuthor();

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        List<AuthorDto> bookDtos = (List<AuthorDto>) apiResponse.getData();

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.SUCCESS, Objects.requireNonNull(apiResponse).getMessage());
        assertEquals(1, bookDtos.size());

    }

    @Test
    void getAllAuthorException_Test() {
        when(authorRepository.findAll()).thenThrow(NullPointerException.class);
        assertThrows(Exception.class, () -> authorService.getAllAuthor());
    }


    @Test
    void getAuthorByIdSuccess_Test() {

        AuthorDao authorDao = AuthorDao.builder()
                .id(1L)
                .name("hendro")
                .build();
        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(authorDao));
        when(modelMapper.map(any(), eq(AuthorDto.class))).thenReturn(AuthorDto.builder()
                .id(1L)
                .authorName("hendro")
                .build());
        ResponseEntity<Object> responseEntity = authorService.getAuthorById(1L);

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.SUCCESS, Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    void getAuthorByIdNotFound_Test() {
        when(authorRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Object> responseEntity = authorService.getAuthorById(1L);

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    void getAuthorByIdException_Test() {
        when(authorRepository.findById(anyLong())).thenThrow(NullPointerException.class);
        assertThrows(Exception.class, () -> authorService.getAuthorById(1L));
    }

    @Test
    void deleteAuthorSuccess_Test() {
        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(AuthorDao.builder()
                .id(1L)
                .name("hendro")
                .build()));
        doNothing().when(authorRepository).delete(any());

        ResponseEntity<Object> responseEntity = authorService.deleteAuthor(1L);

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.SUCCESS, Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    void deleteAuthorNotFound_Test() {
        when(authorRepository.findById(anyLong())).thenReturn(Optional.empty());
        ResponseEntity<Object> responseEntity = authorService.deleteAuthor(1L);

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    void deleteAuthorException_Test() {
        when(authorRepository.findById(anyLong())).thenThrow(NullPointerException.class);
        assertThrows(Exception.class, () -> authorService.deleteAuthor(1L));
    }

    @Test
    void updateBookSuccess_Test() {
        AuthorDao authorDao = AuthorDao.builder()
                .id(1L)
                .build();

        AuthorDto authorDto = AuthorDto.builder()
                .id(1L)
                .authorName("hendro")
                .build();

        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(authorDao));
        when(modelMapper.map(any(), eq(AuthorDao.class))).thenReturn(authorDao);
        when(modelMapper.map(any(), eq(AuthorDto.class))).thenReturn(authorDto);


        ResponseEntity<Object> responseEntity = authorService.updateAuthor(anyLong(), AuthorDto.builder()
                        .authorName("hendro")
                .build());


        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        AuthorDto bookDtoResponse = (AuthorDto) Objects.requireNonNull(apiResponse).getData();

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.SUCCESS, Objects.requireNonNull(apiResponse).getMessage());
        assertEquals(authorDto.getAuthorName(), bookDtoResponse.getAuthorName());
    }

    @Test
    void updateAuthorNotFound_Test() {
        when(authorRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Object> responseEntity = authorService.updateAuthor(anyLong(), AuthorDto.builder()
                .authorName("hendro")
                .build());

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());

    }
    @Test
    void updateAuthorException_Test() {
        when(authorRepository.findById(anyLong())).thenThrow(NullPointerException.class);
        assertThrows(Exception.class, () -> authorService.updateAuthor(anyLong(), AuthorDto.builder()
                .authorName("hendro")
                .build()));



    }
}