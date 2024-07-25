package com.example.SpringMVC_Pageable.service;

import com.example.SpringMVC_Pageable.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    Page<Book> getAllBooks(Pageable pageable);

    Book getBook(Long id);

    Book createBook(Book book);

    Book updateBook(Long id, Book book);

    Long deleteBook(Long id);
}
