package com.winterhold.loan_service.dto.loan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanIndexDto {
    private Long id;
    private String bookTitle;
    private String customerName;
    private String loanDate;
    private String dueDate;
    private String returnDate;
}
