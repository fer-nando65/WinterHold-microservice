package com.winterhold.customer_service.service.implementation;

import com.winterhold.customer_service.dto.customer.CustomerDetailDto;
import com.winterhold.customer_service.dto.customer.CustomerIndexDto;
import com.winterhold.customer_service.dto.customer.CustomerResponseDto;
import com.winterhold.customer_service.dto.customer.CustomerUpsertDto;
import com.winterhold.customer_service.dto.option.OptionDto;
import com.winterhold.customer_service.dto.response.customer.ResponseCustomerDetailDto;
import com.winterhold.customer_service.entity.Customer;
import com.winterhold.customer_service.helper.CustomHelper;
import com.winterhold.customer_service.repository.CustomerRepository;
import com.winterhold.customer_service.service.inf.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CustomerServiceImplementation implements CustomerService {

    private final CustomerRepository repository;
    private final int rowsInPage = 8;

    @Override
    public List<CustomerIndexDto> index(String id, String fullName, String isExpired, Integer page){
        List<Customer> customers;
        Pageable pagination = PageRequest.of(page - 1, rowsInPage);
        if(isExpired.equals("on")){
            customers = repository.getAll(id, fullName, LocalDate.now(), pagination);
        }else {
            customers = repository.getAll(id, fullName, pagination);
        }
        return customers.stream().map(this::mapToCustomerIndexDto).toList();
    }

    private CustomerIndexDto mapToCustomerIndexDto(Customer customer) {
        return CustomerIndexDto.builder()
                .id(customer.getId())
                .fullName(String.format("%s %s", customer.getFirstName(), customer.getLastName()))
                .idExpiredDate(customer.getIdExpiredDate().toString())
                .build();
    }

    @Override
    public CustomerUpsertDto getUpsertCustomer(String id){
        if(!id.isEmpty()){
            var selectedCustomer = repository.getById(id);
            return CustomerUpsertDto.builder()
                    .id(selectedCustomer.getId())
                    .firstName(selectedCustomer.getFirstName())
                    .lastName(selectedCustomer.getLastName())
                    .birthDate(selectedCustomer.getBirthDate())
                    .gender(selectedCustomer.getGender())
                    .phone(selectedCustomer.getPhone())
                    .address(selectedCustomer.getAddress())
                    .genderRadio(buildGenderRadio(selectedCustomer.getGender()))
                    .formFlag("1")
                    .build();
        }else {
            return CustomerUpsertDto.builder()
                    .id(id)
                    .genderRadio(buildGenderRadio(""))
                    .formFlag("0")
                    .build();
        }
    }

    @Override
    public List<OptionDto> buildGenderRadio(String paramGender){
        List<OptionDto> optionGender = new LinkedList<>();
        var genders = Arrays.asList("male", "female");
        for(var gender : genders){
            optionGender.add(OptionDto.builder()
                            .text(gender.substring(0,1).toUpperCase() + gender.substring(1).toLowerCase())
                            .value(gender)
                            .selected(gender.equals(paramGender))
                    .build());
        }

        return optionGender;
    }

    @Override
    public void saveUpsertCustomer(CustomerUpsertDto dto){
        repository.save(Customer.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .birthDate(dto.getBirthDate())
                .gender(dto.getGender())
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .idExpiredDate(LocalDate.now().plusYears(2))
                .build());
    }

    @Override
    public CustomerDetailDto getDetail(String id){
        var selectedCustomer = repository.getById(id);
        return CustomerDetailDto.builder()
                .id(selectedCustomer.getId())
                .fullName(String.format("%s %s", selectedCustomer.getFirstName(), selectedCustomer.getLastName()))
                .birthDate(selectedCustomer.getBirthDate().toString())
                .gender(selectedCustomer.getGender().substring(0,1).toUpperCase() + selectedCustomer.getGender().substring(1).toLowerCase())
                .phone(selectedCustomer.getPhone())
                .address(selectedCustomer.getAddress())
                .build();
    }

    @Override
    public void deleteCustomer(String id){
        repository.delete(repository.getById(id));
    }

    @Override
    public void extendCustomer(String id){
        var selectedCustomer = repository.getById(id);
        selectedCustomer.setIdExpiredDate(selectedCustomer.getIdExpiredDate().plusYears(2));
        repository.save(selectedCustomer);
    }

    @Override
    public Integer getTotalPages(String id, String fullName, String isExpired){
        double totalRows;
        if(isExpired.equals("on")){
            totalRows = this.repository.count(id, fullName, LocalDate.now());
        }else {
            totalRows = this.repository.count(id, fullName);
        }
        return (int)(Math.ceil(totalRows/rowsInPage));
    }

    @Override
    public Boolean isIdTaken(String id){
        return repository.existsById(id);
    }

    @Override
    public List<CustomerResponseDto> getListCustomerByName(String name){
        var customers = repository.getListCustomerByName(name);
        return customers.stream().map(this::mapToCustomerResponseDto).toList();
    }

    private CustomerResponseDto mapToCustomerResponseDto(Customer customer){
        return CustomerResponseDto.builder()
                .id(customer.getId())
                .fullName(CustomHelper.buildFullName(customer.getFirstName(), customer.getLastName()))
                .phone(customer.getPhone())
                .idExpiredDate(customer.getIdExpiredDate())
                .build();
    }

    @Override
    public List<OptionDto> buildCustomerOption(String id){
        var optCustomer = new LinkedList<OptionDto>();
        for(var customer : repository.getCustomers(LocalDate.now())){
            optCustomer.add(OptionDto.builder()
                            .text(CustomHelper.buildFullName(customer.getFirstName(), customer.getLastName()))
                            .value(customer.getId())
                            .selected((Objects.equals(customer.getId(), id)))
                    .build());
        }

        return optCustomer;
    }

    @Override
    public ResponseCustomerDetailDto getDetailCustomer(String id){
        try {
            var selectedCustomer = repository.getById(id);
            return ResponseCustomerDetailDto.builder()
                    .id(selectedCustomer.getId())
                    .fullName(CustomHelper.buildFullName(selectedCustomer.getFirstName(), selectedCustomer.getLastName()))
                    .phone(selectedCustomer.getPhone())
                    .idExpiredDate(selectedCustomer.getIdExpiredDate().toString())
                    .build();
        }catch (Exception e){
            return null;
        }
    }
}
