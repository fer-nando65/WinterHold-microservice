package com.winterhold.loan_service.rest;

import com.winterhold.loan_service.dto.loan.LoanUpsertDto;
import com.winterhold.loan_service.dto.response.base.ResponseWithDataDto;
import com.winterhold.loan_service.service.inf.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/loan")
public class LoanRestController {

    private final LoanService service;

    @GetMapping("/upsert/{id}")
    public ResponseEntity<Object> upsert(@PathVariable Long id){
        try {
            return ResponseEntity.ok(service.upsert(id));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Object> save(@Valid @RequestBody LoanUpsertDto dto,
                                       BindingResult validation){
        try {
            if(validation.hasErrors()){
                return ResponseEntity.unprocessableEntity().body(service.getErrorMessage(validation.getFieldErrors()));
            }else {
                service.save(dto);
                return ResponseEntity.ok(HttpStatus.OK);
            }
        }catch (Exception e ){
            return ResponseEntity.internalServerError().body(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/return/{id}")
    public ResponseEntity<Object> returnBook(@PathVariable Long id){
        try {
            service.returnBook(id);
            return ResponseEntity.ok(HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Object> detail(@PathVariable Long id){
        try {
            var loanDetail = service.getDetail(id);
            if(!Objects.isNull(loanDetail)){
                return ResponseEntity.ok(ResponseWithDataDto.builder()
                        .status(HttpStatus.OK.value())
                        .message("Success")
                        .data(loanDetail)
                        .build());
            }else {
                return ResponseEntity.ok(ResponseWithDataDto.builder()
                        .status(HttpStatus.NO_CONTENT.value())
                        .message("The detail is not found !")
                        .data(null)
                        .build());
            }
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(ResponseWithDataDto.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message("Internal Server Error")
                            .data("error")
                    .build());
        }
    }

    @GetMapping("/get/book/title/{id}")
    public ResponseEntity<Object> getBookTitle(@PathVariable Long id){
        try {
            return ResponseEntity.ok(service.getBookTitle(id));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/status/customer")
    public ResponseEntity<Object> statusCustomer(@RequestParam String customerId){
        try {
            return ResponseEntity.ok(ResponseWithDataDto.builder()
                            .status(HttpStatus.OK.value())
                            .message("Success")
                            .data(service.isCustomerBorrowBook(customerId))
                    .build());
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(
                    ResponseWithDataDto.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message("Internal Server Error")
                            .data("error")
                            .build());
        }
    }
}
