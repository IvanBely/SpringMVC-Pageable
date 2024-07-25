package com.example.SpringMVC_Pageable.service.impl;

import com.example.SpringMVC_Pageable.exception.BookNotFoundException;
import com.example.SpringMVC_Pageable.exception.InsufficientDataException;
import com.example.SpringMVC_Pageable.model.Book;
import com.example.SpringMVC_Pageable.model.repository.BookRepository;
import com.example.SpringMVC_Pageable.service.BookService;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book book;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId(1L);
        book.setTitle("Effective Java");
        book.setGenre("Programming");
        book.setPublicationYear(2008L);
        book.setDescription("A comprehensive guide to programming in Java.");

        pageable = PageRequest.of(0, 10);
    }

    @Test
    void getAllBooks_ValidPageable_ReturnsBooksPage() {
        Page<Book> books = new PageImpl<>(Collections.singletonList(book));
        when(bookRepository.findAll(pageable)).thenReturn(books);

        Page<Book> result = bookService.getAllBooks(pageable);
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(book, result.getContent().get(0));
    }

    @Test
    void getBook_ExistingId_ReturnsBook() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Book result = bookService.getBook(1L);
        assertNotNull(result);
        assertEquals(book, result);
    }

    @Test
    void getBook_NonExistingId_BookNotFoundException() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.getBook(1L));
    }

    @Test
    void createBook_ValidBook_ReturnsCreatedBook() {
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book result = bookService.createBook(book);
        assertNotNull(result);
        assertEquals(book, result);
    }

    @Test
    void updateBook_ExistingId_ValidBook_ReturnsUpdatedBook() {
        Book updatedBook = new Book();
        updatedBook.setTitle("Updated Title");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);

        Book result = bookService.updateBook(1L, updatedBook);
        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
    }

    @Test
    void updateBook_ExistingId_NoFieldsProvided_InsufficientDataException() {
        Book updatedBook = new Book();

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        assertThrows(InsufficientDataException.class, () -> bookService.updateBook(1L, updatedBook));
    }

    @Test
    void updateBook_NonExistingId_BookNotFoundException() {
        Book updatedBook = new Book();
        updatedBook.setTitle("Updated Title");

        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.updateBook(1L, updatedBook));
    }

    @Test
    void deleteBook_ExistingId_ReturnsDeletedBookId() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        doNothing().when(bookRepository).delete(any(Book.class));

        Long result = bookService.deleteBook(1L);
        assertEquals(1L, result);
        verify(bookRepository, times(1)).delete(any(Book.class));
    }

    @Test
    void deleteBook_NonExistingId_BookNotFoundException() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.deleteBook(1L));
    }
}
