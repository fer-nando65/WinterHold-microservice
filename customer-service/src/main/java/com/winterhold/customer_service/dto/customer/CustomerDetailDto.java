package com.winterhold.customer_service.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDetailDto {
    private String id;
    private String fullName;
    private String birthDate;
    private String gender;
    private String phone;
    private String address;
}
