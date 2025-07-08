package com.fpt.pe.controllers;

import com.fpt.pe.apis.MyApiResponse;
import com.fpt.pe.models.BlindBox.*;
import com.fpt.pe.services.BlindBoxService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/blind-boxes")
@RestController
@RequiredArgsConstructor
@Tag(name = "blind-boxes", description = "Blind Box API")
public class ProductController {

    private final BlindBoxService productService;

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        return MyApiResponse.success(productService.getAll());
    }

    @PostMapping("")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity<?> create(@Valid @RequestBody BlindBoxRequest request) {
        return MyApiResponse.success(productService.create(request));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity<?> updateAll(@PathVariable Integer id, @Valid @RequestBody BlindBoxUpdateRequest request) {
        return MyApiResponse.success(productService.updatePartial(id, request));
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity<?> updatePartial(@PathVariable Integer id, @Valid @RequestBody BlindBoxUpdateRequest request) {
        return MyApiResponse.success(productService.updatePartial(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity<?> deleteById(@PathVariable Integer id) {
        productService.deleteById(id);
        return MyApiResponse.noContent();
    }


}
