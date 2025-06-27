package com.fpt.pe.controllers;

import com.fpt.pe.apis.MyApiResponse;
import com.fpt.pe.services.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/categories")
@RestController
@RequiredArgsConstructor
@Tag(name = "categories", description = "Category API")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<?> getAll(){
        return MyApiResponse.success(categoryService.getAll());
    }

}
