package com.alterra.finalproject.service;

import com.alterra.finalproject.constant.AppConstant;
import com.alterra.finalproject.domain.common.ApiResponse;
import com.alterra.finalproject.domain.dao.AuthorDao;
import com.alterra.finalproject.domain.dao.BookDao;
import com.alterra.finalproject.domain.dao.CategoryDao;
import com.alterra.finalproject.domain.dto.AuthorDto;
import com.alterra.finalproject.domain.dto.BookDto;
import com.alterra.finalproject.domain.dto.CategoryDto;
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

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BookService.class)
class BookServiceTest {

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private BookService bookService;



    @Test
    void addBookSuccess_Test() {
        CategoryDao categoryDao = CategoryDao.builder()
                .id(1L)
                .build();

        AuthorDao authorDao = AuthorDao.builder()
                .id(1L)
                .build();

        BookDao bookDao = BookDao.builder()
                .id(1L)
                .build();

        BookDto bookDto = BookDto.builder()
                .id(1L)
                .categoryId(1L)
                .authorId(1L)
                .title("New Product")
                .build();

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(categoryDao));
        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(authorDao));
        when(modelMapper.map(any(), eq(BookDao.class))).thenReturn(bookDao);
        when(modelMapper.map(any(), eq(BookDto.class))).thenReturn(bookDto);
        when(bookRepository.save(any())).thenReturn(bookDao);

        ResponseEntity<Object> responseEntity = bookService.addBook(BookDto.builder()
                .categoryId(1L)
                .authorId(1L)
                .build());

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        BookDto productDtoResponse = (BookDto) Objects.requireNonNull(apiResponse).getData();

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.SUCCESS, Objects.requireNonNull(apiResponse).getMessage());
        assertEquals(bookDto.getTitle(), productDtoResponse.getTitle());
        assertEquals(bookDto.getCategoryId(), productDtoResponse.getCategoryId());

    }


    @Test
    void addBookCategoryIsEmpty_Test() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Object> responseEntity = bookService.addBook(BookDto.builder()
                .categoryId(1L)
                .authorId(1L)
                .build());

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    void addBookAuthorIsEmpty_Test() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(CategoryDao.builder()
                .id(1L)
                .build()));
        when(authorRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Object> responseEntity = bookService.addBook(BookDto.builder()
                .categoryId(1L)
                .authorId(1L)
                .build());

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    void addBookException_Test() {
        when(categoryRepository.findById(anyLong())).thenThrow(NullPointerException.class);
        assertThrows(Exception.class, () -> bookService.addBook(BookDto.builder()
                .categoryId(1L)
                .authorId(1L)
                .build()));


    }

    @Test
    void getAllBookSuccess_Test() {

        BookDao bookDao = BookDao.builder()
                .id(1L)
                .title("naruto")
                .build();
        when(bookRepository.findAll()).thenReturn(List.of(bookDao));
        when(modelMapper.map(any(), eq(BookDto.class))).thenReturn(BookDto.builder()
                .id(1L)
                .title("naruto")
                .build());
        ResponseEntity<Object> responseEntity = bookService.getAllBook();

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        List<BookDto> bookDtos = (List<BookDto>) apiResponse.getData();

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.SUCCESS, Objects.requireNonNull(apiResponse).getMessage());
        assertEquals(1, bookDtos.size());

    }



    @Test
    void getAllBookException_Test() {
        when(bookRepository.findAll()).thenThrow(NullPointerException.class);
        assertThrows(Exception.class, () -> bookService.getAllBook());
    }

    @Test
    void getBookByIdSuccess_Test() {

        BookDao bookDao = BookDao.builder()
                .id(1L)
                .title("naruto")
                .build();
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(bookDao));
        when(modelMapper.map(any(), eq(BookDto.class))).thenReturn(BookDto.builder()
                .id(1L)
                .title("naruto")
                .build());
        ResponseEntity<Object> responseEntity = bookService.getBookById(1L);

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.SUCCESS, Objects.requireNonNull(apiResponse).getMessage());


    }

    @Test
    void getBookByIdNotFound_Test() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Object> responseEntity = bookService.getBookById(1L);

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    void getBookByIdException_Test() {
        when(bookRepository.findById(anyLong())).thenThrow(NullPointerException.class);
        assertThrows(Exception.class, () -> bookService.getBookById(1L));
    }

    @Test
    void deleteBookSuccess_Test() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(BookDao.builder()
                .id(1L)
                .title("naruto")
                .build()));
        doNothing().when(bookRepository).delete(any());

        ResponseEntity<Object> responseEntity = bookService.deleteBook(1L);

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.SUCCESS, Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    void deleteBookNotFound_Test() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());
        ResponseEntity<Object> responseEntity = bookService.deleteBook(1L);

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    void deleteBookException_Test() {
        when(bookRepository.findById(anyLong())).thenThrow(NullPointerException.class);
        assertThrows(Exception.class, () -> bookService.deleteBook(1L));
    }

    @Test
    void updateBookSuccess_Test() {
        BookDao bookDao = BookDao.builder()
                .id(1L)
                .build();

        BookDto bookDto = BookDto.builder()
                .id(1L)
                .categoryId(1L)
                .authorId(1L)
                .title("New Product")
                .build();

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(bookDao));
        when(modelMapper.map(any(), eq(BookDao.class))).thenReturn(bookDao);
        when(modelMapper.map(any(), eq(BookDto.class))).thenReturn(bookDto);


        ResponseEntity<Object> responseEntity = bookService.updateBook(anyLong(), BookDto.builder()
                .title("naruto")
                .description("description")
                .publishDate("2000")
                .price(2000)
                .build());


        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        BookDto bookDtoResponse = (BookDto) Objects.requireNonNull(apiResponse).getData();

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.SUCCESS, Objects.requireNonNull(apiResponse).getMessage());
        assertEquals(bookDto.getTitle(), bookDtoResponse.getTitle());


    }

    @Test
    void updateBookNotFound_Test() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Object> responseEntity = bookService.updateBook(anyLong(), BookDto.builder()
                .title("naruto")
                .description("description")
                .publishDate("2000")
                .price(2000)
                .build());

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
        assertEquals(AppConstant.Message.NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    void updateBookException_Test() {
        when(bookRepository.findById(anyLong())).thenThrow(NullPointerException.class);
        assertThrows(Exception.class, () -> bookService.updateBook(anyLong(), BookDto.builder()
                .title("naruto")
                .description("description")
                .publishDate("2000")
                .price(2000)
                .build()));



    }
}