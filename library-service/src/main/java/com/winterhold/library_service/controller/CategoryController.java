package com.winterhold.library_service.controller;

import com.winterhold.library_service.service.inf.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("category")
public class CategoryController {

    private final CategoryService service;

    @GetMapping("index")
    public String index(@RequestParam(defaultValue = "") String searchCategoryName,
                        @RequestParam(defaultValue = "1") Integer page,
                        Model model){
        var gridCategory = service.getAll(searchCategoryName, page);
        model.addAttribute("gridCategory", gridCategory);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", service.getTotalPages(searchCategoryName));
        model.addAttribute("searchCategoryName", searchCategoryName);
        return "category/category-index";
    }
}
