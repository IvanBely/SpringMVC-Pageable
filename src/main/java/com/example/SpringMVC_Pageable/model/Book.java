package com.example.SpringMVC_Pageable.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "books")
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Title is mandatory", groups = ValidationGroups.Create.class)
    private String title;
    @NotBlank(message = "Genre is mandatory", groups = ValidationGroups.Create.class)
    private String genre;
    @NotNull(message = "Year of publication is mandatory", groups = ValidationGroups.Create.class)
    private Long publicationYear;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    @JsonBackReference
    private Author author;
}
