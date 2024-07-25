package com.example.SpringMVC_Pageable.model.repository;

import com.example.SpringMVC_Pageable.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookRepository extends JpaRepository<Book, Long>, PagingAndSortingRepository<Book, Long> {
}
