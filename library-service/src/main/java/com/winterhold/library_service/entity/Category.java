package com.winterhold.library_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Category")
public class Category {
    @Id
    @Column(name = "CategoryName",length = 50,nullable = false)
    private String categoryName;

    @Column(name = "Floor",nullable = false)
    private Integer floor;

    @Column(name = "Isle",length = 10,nullable = false)
    private String isle;

    @Column(name = "Bay",length = 10,nullable = false)
    private String bay;

    @OneToMany(mappedBy = "category")
    private List<Book> book;
}
