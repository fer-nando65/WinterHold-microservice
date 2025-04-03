package com.winterhold.loan_service.dto.loan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanDetailDto {
    private String title;
    private String categoryName;
    private String authorName;
    private Integer floor;
    private String isle;
    private String bay;
    private String membershipNumber;
    private String fullName;
    private String phoneNumber;
    private String membershipExpiredDate;
}
