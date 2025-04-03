package com.winterhold.library_service.rest;

import com.winterhold.library_service.dto.category.CategoryUpsertDto;
import com.winterhold.library_service.service.inf.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/category")
public class CategoryRestController {

    private final CategoryService service;

    @GetMapping("/upsert/{categoryName}")
    public ResponseEntity<Object> upsert(@PathVariable String categoryName){
        try {
            var categoryDetail = service.getUpsertCategory(categoryName);
            return ResponseEntity.ok(categoryDetail);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Object> save(@Valid @RequestBody CategoryUpsertDto dto,
                                       BindingResult validation){
        try {
            if (validation.hasErrors()){
                return ResponseEntity.unprocessableEntity().body(service.getErrorMessagesValidation(validation));
            }else {
                service.save(dto);
                return ResponseEntity.ok(HttpStatus.OK);
            }
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{categoryName}")
    public ResponseEntity<Object> delete(@PathVariable String categoryName){
        try {
            service.delete(categoryName);
            return ResponseEntity.ok().body(HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
