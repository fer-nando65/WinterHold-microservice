package com.winterhold.library_service.repository;

import com.winterhold.library_service.entity.Author;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    @Query("""
            SELECT aut
            FROM Author aut
            WHERE CONCAT(aut.title, aut.firstName, aut.lastName) LIKE %:name%
            """)
    List<Author> getAll(String name, Pageable pagination);

    @Query("""
            SELECT COUNT(aut)
            FROM Author aut
            WHERE CONCAT(aut.title, aut.firstName, aut.lastName) LIKE %:name%
            """)
    Integer count(String name);

    @Query("""
            SELECT aut
            FROM Author aut
            WHERE aut.id = :id
            """)
    Author getById(Long id);

    @Query("""
            SELECT aut
            FROM Author aut
            """)
    List<Author> requestAuthors();
}
