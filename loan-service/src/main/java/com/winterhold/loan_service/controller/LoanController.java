package com.winterhold.loan_service.controller;

import com.winterhold.loan_service.service.inf.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("loan")
public class LoanController {

    private final LoanService service;

    @GetMapping("index")
    public String index(@RequestParam(defaultValue = "") String searchBookTitle,
                        @RequestParam(defaultValue = "") String searchCustomerName,
                        @RequestParam(defaultValue = "") String isDueDatePassed,
                        @RequestParam(defaultValue = "1") Integer page,
                        Model model){
        var gridLoan = service.index(searchBookTitle, searchCustomerName, isDueDatePassed, page);
        model.addAttribute("gridLoan", gridLoan);
        model.addAttribute("searchBookTitle", searchBookTitle);
        model.addAttribute("searchCustomerName", searchCustomerName);
        model.addAttribute("isDueDatePassed", isDueDatePassed);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", service.getTotalPages(gridLoan.size()));
        return "loan/loan-index";
    }
}
