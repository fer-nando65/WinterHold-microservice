package com.winterhold.loan_service.repository;

import com.winterhold.loan_service.entity.Loan;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    @Query("""
            SELECT loa
            FROM Loan loa
            WHERE loa.bookCode IN :listBookCode
            AND loa.customerNumber IN :listCustomerId
            AND loa.dueDate < :date
            """)
    List<Loan> getAll(List<String> listBookCode, List<String> listCustomerId, LocalDate date, Pageable pagination);

    @Query("""
            SELECT loa
            FROM Loan loa
            WHERE loa.bookCode IN :listBookCode
            AND loa.customerNumber IN :listCustomerId
            """)
    List<Loan> getAll(List<String> listBookCode, List<String> listCustomerId, Pageable pagination);

    @Query("""
            SELECT loa
            FROM Loan loa
            WHERE loa.id = :id
            """)
    Loan getById(Long id);

    @Query("""
            SELECT COUNT(loa)
            FROM Loan loa
            WHERE loa.customerNumber = :customerId
            AND loa.returnDate IS NULL
            """)
    Integer countLoan(String customerId);
}

