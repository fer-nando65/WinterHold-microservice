package com.winterhold.library_service.controller;

import com.winterhold.library_service.dto.book.BookUpsertDto;
import com.winterhold.library_service.service.inf.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("book")
public class BookController {

    private final BookService service;

    @Value("${gateway.url}")
    private String gatewayUrl;

    @GetMapping("index")
    public String index(@RequestParam String categoryName,
                        @RequestParam(defaultValue = "") String searchBookTitle,
                        @RequestParam(defaultValue = "") String searchAuthorName,
                        @RequestParam(defaultValue = "") String isAvailable,
                        @RequestParam(defaultValue = "1") Integer page,
                        Model model){
        var gridBook = service.index(categoryName, searchBookTitle, searchAuthorName, isAvailable, page);
        model.addAttribute("gridBook", gridBook);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", service.getTotalPages(categoryName, searchBookTitle, searchAuthorName, isAvailable));
        model.addAttribute("searchBookTitle", searchBookTitle);
        model.addAttribute("searchAuthorName", searchAuthorName);
        model.addAttribute("isAvailable", isAvailable);
        model.addAttribute("categoryName", categoryName);
        return "book/book-index";
    }

    @GetMapping("upsert")
    public String upsert(@RequestParam String categoryName,
                         @RequestParam(defaultValue = "") String code,
                         Model model){
        var bookDetail = service.getUpsertBook(categoryName, code);
        model.addAttribute("bookDetail", bookDetail);
        model.addAttribute("categoryName", categoryName);
        model.addAttribute("optionAuthor", bookDetail.getListOptionAuthor());
        return "book/book-form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("bookDetail") BookUpsertDto dto,
                       BindingResult validation,
                       Model model){
        if(validation.hasErrors()){
            model.addAttribute("bookDetail", dto);
            model.addAttribute("categoryName", dto.getCategoryName());
            model.addAttribute("optionAuthor", service.buildAuthorOption(dto.getAuthorId()));
            return "book/book-form";
        }else {
            service.save(dto);
            return String.format("redirect:%s/book?categoryName=%s", gatewayUrl, dto.getCategoryName());
        }
    }
}
