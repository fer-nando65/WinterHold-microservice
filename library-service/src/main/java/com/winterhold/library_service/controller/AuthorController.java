package com.winterhold.library_service.controller;

import com.winterhold.library_service.dto.author.AuthorUpsertDto;
import com.winterhold.library_service.service.inf.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("author")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService service;

    @Value("${gateway.url}")
    private String gatewayUrl;

    @GetMapping("index")
    public String index(@RequestParam(defaultValue = "") String searchName,
                        @RequestParam(defaultValue = "1") Integer page,
                        Model model){
        var gridAuthor = service.index(searchName, page);
        model.addAttribute("gridAuthor", gridAuthor);
        model.addAttribute("searchName", searchName);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", service.getTotalPages(searchName));
        return "author/author-index";
    }

    @GetMapping("upsert")
    public String upsert(@RequestParam(defaultValue = "0") Long id,
                         Model model){
        var authorDetail = service.getUpsertAuthor(id);
        model.addAttribute("authorDetail", authorDetail);
        model.addAttribute("optionTitle", authorDetail.getOptionTitle());
        return "author/author-form";
    }

    @PostMapping("save")
    public String save(@Valid @ModelAttribute("authorDetail") AuthorUpsertDto dto,
                       BindingResult validation,
                       Model model){
        if(validation.hasErrors()){
            model.addAttribute("authorDetail");
            model.addAttribute("optionTitle", service.buildTitleSelection(dto.getTitle()));
            return "author/author-form";
        }else {
            service.save(dto);
            return String.format("redirect:%s/customer", gatewayUrl);
        }
    }

    @GetMapping("detail")
    public String detail(@RequestParam Long id,
                         Model model) {
        var authorDetail = service.getDetail(id);
        model.addAttribute("authorDetail", authorDetail);
        model.addAttribute("authorListBook", authorDetail.getListBook());
        return "author/author-detail";
    }
}
