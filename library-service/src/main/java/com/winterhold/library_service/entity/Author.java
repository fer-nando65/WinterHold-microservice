package com.winterhold.library_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "Title",length = 10, nullable = false)
    private String title;

    @Column(name = "FirstName", length = 10, nullable = false)
    private String firstName;

    @Column(name = "LastName", length = 10)
    private String lastName;

    @Column(name = "BirthDate", nullable = false)
    private LocalDate birthDate;

    @Column(name = "DeceasedDate")
    private LocalDate deceasedDate;

    @Column(name = "Education", length = 50)
    private String education;

    @Column(name = "Summary",length = 500)
    private String summary;

    @OneToMany(mappedBy = "author")
    private List<Book> book;
}
