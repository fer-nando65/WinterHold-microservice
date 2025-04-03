package com.winterhold.customer_service.repository;

import com.winterhold.customer_service.entity.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    @Query("""
            SELECT cus
            FROM Customer cus
            WHERE cus.id LIKE %:id%
            AND CONCAT(cus.firstName, " ", cus.lastName) LIKE %:fullName%
            """)
    List<Customer> getAll(String id, String fullName, Pageable page);

    @Query("""
            SELECT cus
            FROM Customer cus
            WHERE cus.id LIKE %:id%
            AND CONCAT(cus.firstName, " ", cus.lastName) LIKE %:fullName%
            AND cus.idExpiredDate < :date
            """)
    List<Customer> getAll(String id, String fullName, LocalDate date, Pageable page);

    @Query("""
            SELECT cus
            FROM Customer cus
            WHERE cus.id = :id
            """)
    Customer getById(String id);

    @Query("""
            SELECT COUNT(cus)
            FROM Customer cus
            WHERE cus.id LIKE %:id%
            AND CONCAT(cus.firstName, " ", cus.lastName) LIKE %:fullName%
            AND cus.idExpiredDate < :date
            """)
    int count(String id, String fullName, LocalDate date);

    @Query("""
            SELECT COUNT(cus)
            FROM Customer cus
            WHERE cus.id LIKE %:id%
            AND CONCAT(cus.firstName, " ", cus.lastName) LIKE %:fullName%
            """)
    int count(String id, String fullName);

    @Query("""
            SELECT cus
            FROM Customer cus
            WHERE CONCAT(cus.firstName, ' ', cus.lastName) LIKE %:name%
            """)
    List<Customer> getListCustomerByName(String name);

    @Query("""
            SELECT cus
            FROM Customer cus
            WHERE cus.idExpiredDate > :date
            """)
    List<Customer> getCustomers(LocalDate date);
}
