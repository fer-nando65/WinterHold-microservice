package com.winterhold.library_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Book")
public class Book {
    @Id
    @Column(name = "Code", length = 8, nullable = false)
    private String code;

    @Column(name = "BookTitle",length = 100,nullable = false)
    private String bookTitle;

    @Column(name = "CategoryName",length = 50,nullable = false)
    private String categoryName;

    @Column(name = "AuthorId",nullable = false)
    private Long authorId;

    @Column(name = "IsBorrowed",nullable = false)
    private Boolean isBorrowed;

    @Column(name = "Summary",length = 500)
    private String summary;

    @Column(name = "ReleaseDate")
    private LocalDate releaseDate;

    @Column(name = "TotalPage")
    private Integer totalPage;

    @Column(name= "IsDeleted")
    private LocalDate isDeleted;

    @ManyToOne
    @JoinColumn(name = "CategoryName", insertable = false, updatable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "AuthorId", insertable = false, updatable = false)
    private Author author;
}
