package com.winterhold.customer_service.service.inf;

import com.winterhold.customer_service.dto.customer.CustomerDetailDto;
import com.winterhold.customer_service.dto.customer.CustomerIndexDto;
import com.winterhold.customer_service.dto.customer.CustomerResponseDto;
import com.winterhold.customer_service.dto.customer.CustomerUpsertDto;
import com.winterhold.customer_service.dto.option.OptionDto;
import com.winterhold.customer_service.dto.response.customer.ResponseCustomerDetailDto;

import java.util.List;

public interface CustomerService {
    List<CustomerIndexDto> index(String id, String fullName, String isExpired, Integer page);
    CustomerUpsertDto getUpsertCustomer(String id);
    void saveUpsertCustomer(CustomerUpsertDto dto);
    CustomerDetailDto getDetail(String id);
    Boolean isIdTaken(String id);
    List<OptionDto> buildGenderRadio(String paramGender);
    Integer getTotalPages(String id, String fullName, String isExpired);
    void deleteCustomer(String id);
    void extendCustomer(String id);
    List<CustomerResponseDto> getListCustomerByName(String name);
    List<OptionDto> buildCustomerOption(String id);
    ResponseCustomerDetailDto getDetailCustomer(String id);
}
