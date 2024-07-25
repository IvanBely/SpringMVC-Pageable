package com.example.SpringMVC_Pageable.service.impl;

import com.example.SpringMVC_Pageable.exception.BookNotFoundException;
import com.example.SpringMVC_Pageable.exception.InsufficientDataException;
import com.example.SpringMVC_Pageable.model.Book;
import com.example.SpringMVC_Pageable.model.repository.BookRepository;
import com.example.SpringMVC_Pageable.service.BookService;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.atn.SemanticContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Override
    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    public Book getBook(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));
    }

    @Override
    public Book createBook(Book book) {
        bookRepository.save(book);
        return book;
    }

    @Override
    public Book updateBook(Long id, Book book) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));

        boolean isAnyFieldProvided = StringUtils.isNotBlank(book.getTitle()) ||
                StringUtils.isNotBlank(book.getGenre()) ||
                book.getPublicationYear() != null ||
                StringUtils.isNotBlank(book.getDescription());

        if (!isAnyFieldProvided) {
            throw new InsufficientDataException("At least one field must be provided for update.");
        }

        if (StringUtils.isNotBlank(book.getTitle())) {
            existingBook.setTitle(book.getTitle());
        }
        if (StringUtils.isNotBlank(book.getGenre())) {
            existingBook.setGenre(book.getGenre());
        }
        if (book.getPublicationYear() != null) {
            existingBook.setPublicationYear(book.getPublicationYear());
        }
        if (StringUtils.isNotBlank(book.getDescription())) {
            existingBook.setDescription(book.getDescription());
        }

        return bookRepository.save(existingBook);
    }

    @Override
    public Long deleteBook(Long id) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));
        bookRepository.delete(existingBook);
        return id;
    }
}
