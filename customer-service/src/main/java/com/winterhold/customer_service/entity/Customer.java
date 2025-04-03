package com.winterhold.customer_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "Customer")
public class Customer {
    @Id
    @Column(name = "Id", length = 8, nullable = false)
    private String id;

    @Column(name = "FirstName", length = 10, nullable = false)
    private String firstName;

    @Column(name = "LastName", length = 10)
    private String lastName;

    @Column(name = "BirthDate", nullable = false)
    private LocalDate birthDate;

    @Column(name = "Gender", length = 10, nullable = false)
    private String gender;

    @Column(name = "Phone", length = 20)
    private String phone;

    @Column(name = "Address", length = 100)
    private String address;

    @Column(name = "IdExpiredDate", nullable = false)
    private LocalDate idExpiredDate;
}
