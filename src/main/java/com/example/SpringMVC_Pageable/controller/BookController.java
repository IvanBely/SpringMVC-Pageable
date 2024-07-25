package com.example.SpringMVC_Pageable.controller;

import com.example.SpringMVC_Pageable.model.Book;
import com.example.SpringMVC_Pageable.model.ValidationGroups;
import com.example.SpringMVC_Pageable.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    @GetMapping("/")
    public ResponseEntity<Page<Book>> getAllBooks(Pageable pageable) {
        Page<Book> books = bookService.getAllBooks(pageable);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id) {
        Book book = bookService.getBook(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }
    @PostMapping("/")
    public ResponseEntity<Book> createBook(@Validated(ValidationGroups.Create.class) @RequestBody Book book) {
        Book createBook = bookService.createBook(book);
        return new ResponseEntity<>(createBook, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id,@Validated(ValidationGroups.Update.class) @RequestBody Book book) {
        Book updateBook = bookService.updateBook(id, book);
        return new ResponseEntity<>(updateBook, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteBook(@PathVariable Long id) {
        Long deleteBookId = bookService.deleteBook(id);
        return new ResponseEntity<>(deleteBookId, HttpStatus.OK);
    }

}
