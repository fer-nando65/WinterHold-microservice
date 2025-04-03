package com.winterhold.customer_service.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerIndexDto {
    private String id;
    private String fullName;
    private String idExpiredDate;
}
