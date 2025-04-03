package com.winterhold.loan_service.service.inf;

import com.winterhold.loan_service.dto.loan.LoanDetailDto;
import com.winterhold.loan_service.dto.loan.LoanIndexDto;
import com.winterhold.loan_service.dto.loan.LoanUpsertDto;
import com.winterhold.loan_service.dto.loan.LoanValidationDto;
import com.winterhold.loan_service.dto.option.OptionDto;
import org.springframework.validation.FieldError;

import java.util.List;

public interface LoanService {
    List<LoanIndexDto> index(String bookTitle, String customerName, String isDueDatePassed, Integer page);
    Integer getTotalPages(Integer totalRows);
    LoanUpsertDto upsert(Long id);
    List<OptionDto> fetchBuildOptionCustomer(String id);
    List<OptionDto> fetchBuildBookOption(String bookCode);
    void save(LoanUpsertDto dto);
    void returnBook(Long id);
    LoanDetailDto getDetail(Long id);
    String getBookTitle(Long id);
    LoanValidationDto getErrorMessage(List<FieldError> validation);
    Boolean isCustomerBorrowBook(String customerId);
}
