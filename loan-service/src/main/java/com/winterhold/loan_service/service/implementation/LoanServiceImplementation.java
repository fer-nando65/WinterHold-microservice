package com.winterhold.loan_service.service.implementation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.winterhold.base.dto.LoanNotificationDto;
import com.winterhold.loan_service.dto.loan.LoanDetailDto;
import com.winterhold.loan_service.dto.loan.LoanIndexDto;
import com.winterhold.loan_service.dto.loan.LoanUpsertDto;
import com.winterhold.loan_service.dto.loan.LoanValidationDto;
import com.winterhold.loan_service.dto.option.OptionDto;
import com.winterhold.loan_service.dto.response.base.ResponseDto;
import com.winterhold.loan_service.dto.response.book.ResponseBookDetailDto;
import com.winterhold.loan_service.dto.response.book.BookResponseDto;
import com.winterhold.loan_service.dto.response.customer.CustomerResponseDto;
import com.winterhold.loan_service.dto.response.base.ResponseWithDataDto;
import com.winterhold.loan_service.dto.response.customer.ResponseCustomerDetailDto;
import com.winterhold.loan_service.entity.Loan;
import com.winterhold.loan_service.repository.LoanRepository;
import com.winterhold.loan_service.service.inf.LoanService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanServiceImplementation implements LoanService {

    @Autowired
    @Value("${spring.kafka.topic.loan}")
    private String loanTopic;

    private final KafkaTemplate<String, LoanNotificationDto> loanKafkaMessage;

    private final LoanRepository repository;
    private final WebClient.Builder webClientBuilder;
    private final ObjectMapper objectMapper;
    private final Integer rowsInPage = 8;

    public List<LoanIndexDto> index(String bookTitle, String customerName, String isDueDatePassed, Integer page){
        Pageable pagination = PageRequest.of(page - 1, rowsInPage);

        CompletableFuture<List<BookResponseDto>> booksFuture = CompletableFuture
                .supplyAsync(()-> fetchBook(bookTitle))
                .exceptionally(this::fallbackBooks);
        CompletableFuture<List<CustomerResponseDto>> customersFuture = CompletableFuture
                .supplyAsync(()-> fetchCustomer(customerName))
                .exceptionally(this::fallbackCustomers);

        var listBookResponse = booksFuture.join();
        var listCustomerResponse = customersFuture.join();

        if(listBookResponse.isEmpty() || listCustomerResponse.isEmpty()){
            return new LinkedList<>();
        }

        List<Loan> loans;
        if(!Objects.equals(listBookResponse.size(), 0) && !Objects.equals(listCustomerResponse.size(), 0)){
            if(isDueDatePassed.equals("on")){
                loans = repository.getAll(
                        listBookResponse.stream().map(BookResponseDto::getCode).toList(),
                        listCustomerResponse.stream().map(CustomerResponseDto::getId).toList(),
                        LocalDate.now(),
                        pagination);
            }else {
                loans = repository.getAll(
                        listBookResponse.stream().map(BookResponseDto::getCode).toList(),
                        listCustomerResponse.stream().map(CustomerResponseDto::getId).toList(),
                        pagination);
            }
            if(!loans.isEmpty()){
                return mapToListLoanIndexDto(loans, listBookResponse,listCustomerResponse);
            }else {
                return new LinkedList<>();
            }
        }else {
            return new LinkedList<>();
        }
    }

    @CircuitBreaker(name = "loanService", fallbackMethod = "fallbackBooks")
    @TimeLimiter(name = "loanService", fallbackMethod = "fallbackBooks")
    public List<BookResponseDto> fetchBook(String bookTitle){
        return Arrays.asList(webClientBuilder.build()
                .get()
                .uri("http://library-service/api/book",
                        uriBuilder -> uriBuilder.queryParam("title", bookTitle).build())
                .retrieve()
                .bodyToMono(BookResponseDto[].class)
                .block()
        );
    }

    @CircuitBreaker(name = "loanService", fallbackMethod = "fallbackCustomers")
    @TimeLimiter(name = "loanService", fallbackMethod = "fallbackCustomers")
    public List<CustomerResponseDto> fetchCustomer(String customerName){
        return Arrays.asList(
                webClientBuilder.build()
                        .get()
                        .uri("http://customer-service/api/customer",
                                uriBuilder -> uriBuilder.queryParam("name", customerName).build())
                        .retrieve()
                        .bodyToMono(CustomerResponseDto[].class)
                        .block()
        );
    }

    private List<BookResponseDto> fallbackBooks(Throwable t){
        return new ArrayList<>();
    }

    private List<CustomerResponseDto> fallbackCustomers(Throwable t){
        return new ArrayList<>();
    }

    private List<LoanIndexDto> mapToListLoanIndexDto(List<Loan> loans,
                                                     List<BookResponseDto> listBook,
                                                     List<CustomerResponseDto> listCustomer){
        Map<String, BookResponseDto> mapBook = listBook.stream()
                .collect(Collectors.toMap(
                        BookResponseDto::getCode,
                        book -> book
                ));

        Map<String, CustomerResponseDto> mapCustomer = listCustomer.stream()
                .collect(Collectors.toMap(
                        CustomerResponseDto::getId,
                        customer -> customer
                ));
        List<LoanIndexDto> dto = new LinkedList<>();
        for(var loan : loans){
            var selectedBook = mapBook.get(loan.getBookCode());
            var selectedCustomer = mapCustomer.get(loan.getCustomerNumber());
            dto.add(LoanIndexDto.builder()
                            .id(loan.getId())
                            .bookTitle(selectedBook.getBookTitle())
                            .customerName(selectedCustomer.getFullName())
                            .loanDate(loan.getLoanDate().toString())
                            .dueDate(loan.getDueDate().toString())
                            .returnDate((loan.getReturnDate() != null) ? loan.getReturnDate().toString() : "-")
                    .build());
        }
        return dto;
    }

    @Override
    public Integer getTotalPages(Integer totalRows){
        return (int)(Math.ceil((double) totalRows / rowsInPage));
    }

    @Override
    public LoanUpsertDto upsert(Long id){
        if(id != 0){
            var selectedLoan = repository.getById(id);

            CompletableFuture<List<OptionDto>> customerOptionFuture = CompletableFuture
                    .supplyAsync(()  -> fetchBuildOptionCustomer(selectedLoan.getCustomerNumber()))
                    .exceptionally(this::fallbackBuildCustomerOption);

            CompletableFuture<List<OptionDto>> bookOptionFuture = CompletableFuture
                    .supplyAsync(() -> fetchBuildBookOption(selectedLoan.getBookCode()))
                    .exceptionally(this::fallbackBuildCustomerOption);

            var optCustomer = customerOptionFuture.join();
            var optBook = bookOptionFuture.join();

            return LoanUpsertDto.builder()
                    .id(selectedLoan.getId())
                    .customerId(selectedLoan.getCustomerNumber())
                    .bookCode(selectedLoan.getBookCode())
                    .loanDate(selectedLoan.getLoanDate())
                    .note(selectedLoan.getNote())
                    .listCustomer(optCustomer)
                    .listBook(optBook)
                    .build();
        }else {
            return LoanUpsertDto.builder()
                    .id(id)
                    .listCustomer(fetchBuildOptionCustomer("-1"))
                    .listBook(fetchBuildBookOption("-1"))
                    .build();
        }
    }

    @Override
    @CircuitBreaker(name = "loanService", fallbackMethod = "fallbackBuildCustomerOption")
    @TimeLimiter(name = "loanService", fallbackMethod = "fallbackBuildCustomerOption")
    public List<OptionDto> fetchBuildOptionCustomer(String id) throws RuntimeException{
        var response = webClientBuilder.build()
                .get()
                .uri("http://customer-service/api/customer/option",
                        uriBuilder -> uriBuilder.queryParam("idOption", id).build())
                .retrieve()
                .bodyToMono(ResponseWithDataDto.class)
                .block();

        if(Objects.equals(response.getStatus(), 200)){
            return objectMapper.convertValue(response.getData(), new TypeReference<LinkedList<OptionDto>>() {});
        }else {
            throw new RuntimeException();
        }
    }

    private List<OptionDto> fallbackBuildCustomerOption(Throwable t){
        return new LinkedList<>();
    }

    @Override
    @CircuitBreaker(name = "loanService", fallbackMethod = "fallbackBuildCustomerOption")
    @TimeLimiter(name = "loanService", fallbackMethod = "fallbackBuildCustomerOption")
    public List<OptionDto> fetchBuildBookOption(String bookCode) throws RuntimeException{
        var response = webClientBuilder.build()
                .get()
                .uri("http://library-service/api/book/option",
                        uriBuilder -> uriBuilder.queryParam("bookCodeOption", bookCode).build())
                .retrieve()
                .bodyToMono(ResponseWithDataDto.class)
                .block();

        if(Objects.equals(response.getStatus(), 200)){
            return objectMapper.convertValue(response.getData(), new TypeReference<LinkedList<OptionDto>>() {});
        }else {
            throw new RuntimeException();
        }
    }

    private List<OptionDto> fallbackBuildBookOption(Throwable t){
        return new LinkedList<>();
    }

    @Override
    public void save(LoanUpsertDto dto){
        ResponseDto response;
        var oldLoan = repository.getById(dto.getId());
        if(!Objects.isNull(oldLoan)) {
            String bookCode = oldLoan.getBookCode();
            if(!Objects.equals(oldLoan.getBookCode(), dto.getBookCode())){
                bookCode = dto.getBookCode();
                response = webClientBuilder.build()
                        .get()
                        .uri("http://library-service/api/book/borrow",
                                uriBuilder -> uriBuilder
                                        .queryParam("firstBook", dto.getBookCode())
                                        .queryParam("secondBook", oldLoan.getBookCode())
                                        .build())
                        .retrieve()
                        .bodyToMono(ResponseDto.class)
                        .block();
            }else {
                response = ResponseDto.builder()
                        .status(200)
                        .build();
            }

            if(Objects.equals(response.getStatus(), 200)){
                repository.save(Loan.builder()
                        .id(dto.getId())
                        .bookCode(bookCode)
                        .customerNumber(dto.getCustomerId())
                        .loanDate(dto.getLoanDate())
                        .dueDate(dto.getLoanDate().plusDays(7))
                        .note(dto.getNote())
                        .build());

                loanKafkaMessage.send(
                        loanTopic,
                        LoanNotificationDto.builder()
                                .message(String.format("Successfully Edit Loan"))
                                .build()
                );
            }
        }else {
            response = webClientBuilder.build()
                    .get()
                    .uri("http://library-service/api/book/borrow",
                            uriBuilder -> uriBuilder.queryParam("firstBook", dto.getBookCode()).build())
                    .retrieve()
                    .bodyToMono(ResponseDto.class)
                    .block();

            if(Objects.equals(response.getStatus(), 200)){
                repository.save(Loan.builder()
                        .bookCode(dto.getBookCode())
                        .customerNumber(dto.getCustomerId())
                        .loanDate(dto.getLoanDate())
                        .dueDate(dto.getLoanDate().plusDays(7))
                        .note(dto.getNote())
                        .build()
                );

                loanKafkaMessage.send(
                        loanTopic,
                        LoanNotificationDto.builder()
                                .message(String.format("Successfully Add Loan"))
                                .build()
                );
            }
        }
    }

    @Override
    public void returnBook(Long id) throws RuntimeException{
        var selectedLoan = repository.getById(id);
        var status = webClientBuilder.build()
                .get()
                .uri("http://library-service/api/book/borrow",
                        uriBuilder -> uriBuilder.queryParam("secondBook", selectedLoan.getBookCode()).build())
                .retrieve()
                .bodyToMono(ResponseDto.class)
                .block();

        if(!Objects.equals(status.getStatus(), 200)){
            throw new RuntimeException();
        }

        selectedLoan.setReturnDate(LocalDate.now());
        repository.save(selectedLoan);
    }

    @Override
    public LoanDetailDto getDetail(Long id) throws RuntimeException{
        var selectedLoan = repository.getById(id);
        if(Objects.isNull(selectedLoan)){
            throw new RuntimeException();
        }
        var responseBook = webClientBuilder.build()
                .get()
                .uri("http://library-service/api/book/detail",
                        uriBuilder -> uriBuilder.queryParam("code", selectedLoan.getBookCode()).build())
                .retrieve()
                .bodyToMono(ResponseWithDataDto.class)
                .block();

        var responseCustomer = webClientBuilder.build()
                .get()
                .uri("http://customer-service/api/customer/detail",
                        uriBuilder -> uriBuilder.queryParam("id", selectedLoan.getCustomerNumber()).build())
                .retrieve()
                .bodyToMono(ResponseWithDataDto.class)
                .block();

        ResponseBookDetailDto bookDetail = objectMapper.convertValue(responseBook.getData(), ResponseBookDetailDto.class);
        ResponseCustomerDetailDto customerDetail = objectMapper.convertValue(responseCustomer.getData(), ResponseCustomerDetailDto.class);

        return LoanDetailDto.builder()
                .title(bookDetail.getTitle())
                .categoryName(bookDetail.getCategoryName())
                .authorName(bookDetail.getAuthorName())
                .floor(bookDetail.getFloor())
                .isle(bookDetail.getIsle())
                .bay(bookDetail.getBay())
                .membershipNumber(customerDetail.getId())
                .fullName(customerDetail.getFullName())
                .phoneNumber(customerDetail.getPhone())
                .membershipExpiredDate(customerDetail.getIdExpiredDate())
                .build();
    }

    @Override
    public String getBookTitle(Long id){
        return webClientBuilder.build()
                .get()
                .uri("http://library-service/api/book/client/title",
                        uriBuilder -> uriBuilder.queryParam("code", repository.getById(id).getBookCode()).build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @Override
    public LoanValidationDto getErrorMessage(List<FieldError> validation){
        var errorMessageDto = new LoanValidationDto();
        for(var entity : validation){
            if(Objects.equals(entity.getField(), "customerId")){
                errorMessageDto.setErrorCustomer(entity.getDefaultMessage());
            }

            if(Objects.equals(entity.getField(), "bookCode")){
                errorMessageDto.setErrorBook(entity.getDefaultMessage());
            }

            if(Objects.equals(entity.getField(), "loanDate")){
                errorMessageDto.setErrorLoanDate(entity.getDefaultMessage());
            }
        }
        return errorMessageDto;
    }

    @Override
    public Boolean isCustomerBorrowBook(String customerId){
        Integer count = repository.countLoan(customerId);
        if(count > 0){
            return true;
        }else
            return false;
    }
}
