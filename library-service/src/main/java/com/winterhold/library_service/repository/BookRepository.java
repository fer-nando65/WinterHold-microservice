package com.winterhold.library_service.repository;

import com.winterhold.library_service.entity.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {

    @Query("""
            SELECT boo
            FROM Book boo
            JOIN boo.category cat
            JOIN boo.author aut
            WHERE cat.categoryName = :categoryName
            AND boo.bookTitle LIKE %:bookTitle%
            AND CONCAT(aut.title, aut.firstName, aut.lastName) LIKE %:authorName%
            AND boo.isBorrowed = false
            AND boo.isDeleted IS NULL
            """)
    List<Book> getAll(String categoryName, String bookTitle, String authorName, String isBorrowed, Pageable pagination);

    @Query("""
            SELECT boo
            FROM Book boo
            JOIN boo.category cat
            JOIN boo.author aut
            WHERE cat.categoryName = :categoryName
            AND boo.bookTitle LIKE %:bookTitle%
            AND CONCAT(aut.title, aut.firstName, aut.lastName) LIKE %:authorName%
            AND boo.isDeleted IS NULL
            """)
    List<Book> getAll(String categoryName, String bookTitle, String authorName, Pageable pagination);

    @Query("""
            SELECT COUNT(boo)
            FROM Book boo
            JOIN boo.category cat
            JOIN boo.author aut
            WHERE cat.categoryName = :categoryName
            AND boo.bookTitle LIKE %:bookTitle%
            AND CONCAT(aut.title, aut.firstName, aut.lastName) LIKE %:authorName%
            AND boo.isBorrowed = false
            AND boo.isDeleted IS NULL
            """)
    Integer count(String categoryName, String bookTitle, String authorName, String isBorrowed);

    @Query("""
            SELECT COUNT(boo)
            FROM Book boo
            JOIN boo.category cat
            JOIN boo.author aut
            WHERE cat.categoryName = :categoryName
            AND boo.bookTitle LIKE %:bookTitle%
            AND CONCAT(aut.title, aut.firstName, aut.lastName) LIKE %:authorName%
            AND boo.isDeleted IS NULL
            """)
    Integer count(String categoryName, String bookTitle, String authorName);

    @Query("""
           SELECT boo
           FROM Book boo
           WHERE boo.code = :code
           """)
    Book getById(String code);

    @Query("""
            SELECT boo
            FROM Book boo
            WHERE boo.bookTitle LIKE %:title%
            """)
    List<Book> getListBookByTitle(String title);

    @Query("""
            SELECT boo
            FROM Book boo
            WHERE boo.isBorrowed = false
            AND boo.isDeleted IS NULL
            """)
    List<Book> getBooks();
}
