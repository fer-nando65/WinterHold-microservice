package com.winterhold.library_service.rest;

import com.winterhold.library_service.dto.response.ResponseDto;
import com.winterhold.library_service.dto.response.ResponseWithDataDto;
import com.winterhold.library_service.service.inf.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book")
public class BookRestController {

    private final BookService service;

    @GetMapping("/summary/{code}")
    public ResponseEntity<Object> getSummary(@PathVariable String code){
        try {
            return ResponseEntity.ok(service.getSummary(code));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{code}")
    public ResponseEntity<Object> delete(@PathVariable String code){
        try {
            service.delete(code);
            return ResponseEntity.ok(HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/title/{code}")
    public ResponseEntity<Object> getBookTitle(@PathVariable String code){
        try {
            return ResponseEntity.ok(service.getBookTitle(code));
        }catch (Exception e ){
            return ResponseEntity.internalServerError().body(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<Object> getListBookByTitle(@RequestParam String title){
        try {
            return ResponseEntity.ok(service.getListBookByTitle(title));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/option")
    public ResponseEntity<Object> buildBookLoanOption(@RequestParam String bookCodeOption){
        try {
            return ResponseEntity.ok(ResponseWithDataDto.builder()
                            .status(200)
                            .message("Success")
                            .data(service.buildBookOption(bookCodeOption))
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

    @GetMapping("/borrow")
    public ResponseEntity<Object> changeIsBorrowedBook(@RequestParam(defaultValue = "") String firstBook,
                                                       @RequestParam(defaultValue = "") String secondBook){
        try {
            service.changeIsBorrowedBook(firstBook, secondBook);
            return ResponseEntity.ok(ResponseDto.builder()
                            .status(HttpStatus.OK.value())
                            .message("Success")
                    .build());
        }catch (Exception e ){
            return ResponseEntity.internalServerError().body(ResponseDto.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message("Internal Server Error")
                    .build());
        }
    }

    @GetMapping("/client/title")
    public ResponseEntity<Object> getClientBookTitle(@RequestParam String code){
        try {
            return ResponseEntity.ok(service.getBookTitle(code));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/detail")
    public ResponseEntity<Object> getBookDetail(@RequestParam String code){
        try {
            var detail = service.getDetailBook(code);
            ResponseWithDataDto response;
            if(!Objects.isNull(detail)){
                response = ResponseWithDataDto.builder()
                        .status(HttpStatus.OK.value())
                        .message("Success")
                        .data(detail)
                        .build();
            }else {
                response = ResponseWithDataDto.builder()
                        .status(HttpStatus.NO_CONTENT.value())
                        .message("No Content")
                        .data(null)
                        .build();
            }
            return ResponseEntity.ok(response);
        }catch (Exception e) {
            var response = ResponseWithDataDto.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Internal Server Error")
                    .data("error")
                    .build();
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
