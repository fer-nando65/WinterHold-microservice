package com.winterhold.customer_service.controller;

import com.winterhold.customer_service.dto.customer.CustomerUpsertDto;
import com.winterhold.customer_service.service.inf.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("customer")
public class CustomerController {

    private final CustomerService service;

    @Value("${gateway.url}")
    private String gatewayUrl;

    @GetMapping("index")
    public String index(@RequestParam(defaultValue = "") String searchId,
                        @RequestParam(defaultValue = "") String searchFullName,
                        @RequestParam(defaultValue = "") String isExpired,
                        @RequestParam(defaultValue = "1") Integer page,
                        Model model){
        var gridCustomerIndex = service.index(searchId, searchFullName, isExpired, page);
        model.addAttribute("gridCustomer", gridCustomerIndex);
        model.addAttribute("searchId", searchId);
        model.addAttribute("searchFullName", searchFullName);
        model.addAttribute("isExpired", isExpired);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", service.getTotalPages(searchId, searchFullName, isExpired));
        return "customer/customer-index";
    }

    @GetMapping("upsert")
    public String upsert(@RequestParam(defaultValue = "") String id,
                         Model model){
        var customerDetail = service.getUpsertCustomer(id);
        model.addAttribute("customerDetail", customerDetail);
        model.addAttribute("genderRadioButton", customerDetail.getGenderRadio());
        return "customer/customer-form";
    }

    @PostMapping("save")
    public String save(@Valid @ModelAttribute("customerDetail") CustomerUpsertDto dto,
                       BindingResult validation,
                       Model model){
        if(validation.hasErrors()){
            if(!dto.getId().isEmpty() && !dto.getFormFlag().equals("0")){
                var detail = service.getUpsertCustomer(dto.getId());
                model.addAttribute("genderRadioButton", detail.getGenderRadio());
            }else {
                model.addAttribute("genderRadioButton", service.buildGenderRadio(dto.getGender()));
            }
            model.addAttribute("customerDetail", dto);
            return "customer/customer-form";
        }else {
            service.saveUpsertCustomer(dto);
            return String.format("redirect:%s/customer", gatewayUrl);
        }
    }
}
