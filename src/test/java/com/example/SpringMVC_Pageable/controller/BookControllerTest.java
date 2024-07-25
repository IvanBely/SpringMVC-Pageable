package com.example.SpringMVC_Pageable.controller;

import com.example.SpringMVC_Pageable.model.Book;
import com.example.SpringMVC_Pageable.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    private Book book;

    @BeforeEach
    public void setup() {
        book = new Book();
        book.setId(1L);
        book.setTitle("Effective Java");
        book.setGenre("Programming");
        book.setPublicationYear(2008L);
        book.setDescription("A comprehensive guide to programming in Java.");
    }

    @Test
    public void getAllBooks_ValidPageable_ReturnsBooksPage() throws Exception {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("title").ascending());
        Page<Book> books = new PageImpl<>(Collections.singletonList(book), pageable, 1);
        when(bookService.getAllBooks(pageable)).thenReturn(books);

        mockMvc.perform(get("/")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "title,asc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Effective Java"))
                .andExpect(jsonPath("$.content[0].genre").value("Programming"))
                .andExpect(jsonPath("$.content[0].publicationYear").value(2008))
                .andExpect(jsonPath("$.content[0].description").value("A comprehensive guide to programming in Java."));
    }

    @Test
    public void getBook_ExistingId_ReturnsBook() throws Exception {
        when(bookService.getBook(1L)).thenReturn(book);

        mockMvc.perform(get("/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Effective Java"))
                .andExpect(jsonPath("$.genre").value("Programming"))
                .andExpect(jsonPath("$.publicationYear").value(2008))
                .andExpect(jsonPath("$.description").value("A comprehensive guide to programming in Java."));
    }

    @Test
    public void createBook_ValidBook_ReturnsCreatedBook() throws Exception {
        when(bookService.createBook(any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(book)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Effective Java"))
                .andExpect(jsonPath("$.genre").value("Programming"))
                .andExpect(jsonPath("$.publicationYear").value(2008))
                .andExpect(jsonPath("$.description").value("A comprehensive guide to programming in Java."));
    }

    @Test
    public void updateBook_ExistingId_ValidBook_ReturnsUpdatedBook() throws Exception {
        when(bookService.updateBook(eq(1L), any(Book.class))).thenReturn(book);

        mockMvc.perform(put("/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Effective Java"))
                .andExpect(jsonPath("$.genre").value("Programming"))
                .andExpect(jsonPath("$.publicationYear").value(2008))
                .andExpect(jsonPath("$.description").value("A comprehensive guide to programming in Java."));
    }

    @Test
    public void deleteBook_ExistingId_ReturnsDeletedBookId() throws Exception {
        Long bookId = 1L;
        when(bookService.deleteBook(bookId)).thenReturn(bookId);

        mockMvc.perform(delete("/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(bookId.toString()));
    }
}
