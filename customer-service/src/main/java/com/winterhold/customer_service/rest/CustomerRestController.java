package com.winterhold.customer_service.rest;

import com.winterhold.customer_service.dto.response.base.ResponseWithDataDto;
import com.winterhold.customer_service.service.inf.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")
public class CustomerRestController {

    private final CustomerService service;

    @GetMapping("/detail/{id}")
    public ResponseEntity<Object> getDetail(@PathVariable String id){
        try{
            return ResponseEntity.ok(service.getDetail(id));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable String id){
        try {
            service.deleteCustomer(id);
            return ResponseEntity.ok(HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/extend/{id}")
    public ResponseEntity<Object> extendCustomer(@PathVariable String id){
        try {
            service.extendCustomer(id);
            return ResponseEntity.ok().body(HttpStatus.OK);
        }catch (Exception e ){
            return ResponseEntity.internalServerError().body(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<Object> getListCustomerByName(@RequestParam String name){
        try {
            return ResponseEntity.ok(service.getListCustomerByName(name));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/option")
    public ResponseEntity<Object> buildCustomerLoanOption(@RequestParam String idOption){
        try {
            return ResponseEntity.ok(ResponseWithDataDto.builder()
                            .status(HttpStatus.OK.value())
                            .message("Success")
                            .data(service.buildCustomerOption(idOption))
                    .build());
        }catch (Exception e ){
            return ResponseEntity.internalServerError().body(
                    ResponseWithDataDto.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message("Internal Server Error")
                            .data("error")
                            .build()
            );
        }
    }

    @GetMapping("/detail")
    public ResponseEntity<Object> detailCustomer(@RequestParam String id){
        try {
            var detailCustomer = service.getDetailCustomer(id);
            if(!Objects.isNull(detailCustomer)){
                return ResponseEntity.ok(ResponseWithDataDto.builder()
                        .status(HttpStatus.OK.value())
                        .message("Success")
                        .data(detailCustomer)
                        .build());
            }else {
                return ResponseEntity.ok(ResponseWithDataDto.builder()
                        .status(HttpStatus.NO_CONTENT.value())
                        .message("The data is not found !")
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
}
