package com.example.SpringMVC_Pageable.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "authors")
@Data
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name is mandatory", groups = ValidationGroups.Create.class)
    private String name;
    @NotBlank(message = "Biography is mandatory", groups = ValidationGroups.Create.class)
    private String biography;
//    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonManagedReference
//    private List<Book> bookList;

}
