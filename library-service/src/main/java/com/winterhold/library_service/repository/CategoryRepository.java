package com.winterhold.library_service.repository;

import com.winterhold.library_service.entity.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    @Query("""
            SELECT cat
            FROM Category cat
            WHERE cat.categoryName LIKE %:categoryName%
            """)
    List<Category> getAll(String categoryName, Pageable pagination);

    @Query("""
            SELECT cat
            FROM Category cat
            WHERE cat.categoryName = :categoryName
            """)
    Category getById(String categoryName);

    @Query("""
            SELECT COUNT(cat)
            FROM Category cat
            WHERE cat.categoryName LIKE %:categoryName%
            """)
    Integer count(String categoryName);
}
