package com.example.SpringMVC_Pageable.model.repository;

import com.example.SpringMVC_Pageable.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AuthorRepository extends JpaRepository<Author, Long>, PagingAndSortingRepository<Author, Long> {
}
