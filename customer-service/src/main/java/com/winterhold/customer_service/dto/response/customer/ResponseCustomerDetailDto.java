package com.winterhold.customer_service.dto.response.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseCustomerDetailDto {
    private String id;
    private String fullName;
    private String phone;
    private String idExpiredDate;
}
