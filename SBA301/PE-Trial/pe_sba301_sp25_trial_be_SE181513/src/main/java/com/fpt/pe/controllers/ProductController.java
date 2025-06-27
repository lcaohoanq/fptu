package com.fpt.pe.controllers;

import com.fpt.pe.apis.MyApiResponse;
import com.fpt.pe.models.BlindBox;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/blind-boxes")
@RestController
@RequiredArgsConstructor
@Tag(name = "blind-boxes", description = "Blind Box API")
public class ProductController {

    public record BlindBoxRequest(
        @NotBlank(message = "Name is required")
        @Size(min = 10, message = "Name must be greater than 10 characters")
        String name,

        @NotBlank(message = "Rarity is required")
        String rarity,

        @NotNull(message = "Price is required")
        Double price,

        @NotNull(message = "Stock is required")
        @Min(value = 1, message = "Stock must be at least 1")
        @Max(value = 100, message = "Stock must be at most 100")
        Integer stock,

        @NotNull(message = "Category ID is required")
        Integer categoryId
    ) {}

    public record BlindBoxResponse(
        Integer id,
        String name,
        String rarity,
        Double price,
        Date releaseDate,
        Integer stock,
        String categoryName) {

        public static BlindBoxResponse from(BlindBox blindBox) {
            return new BlindBoxResponse(
                blindBox.getId(),
                blindBox.getName(),
                blindBox.getRarity(),
                blindBox.getPrice(),
                blindBox.getReleaseDate(),
                blindBox.getStock(),
                blindBox.getCategory() != null ? blindBox.getCategory().getName() : null
            );
        }

    }

    private final BlindBoxService productService;

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        return MyApiResponse.success(productService.getAll());
    }

    @PostMapping("")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity<?> create(@Valid @RequestBody BlindBoxRequest request) {
        productService.create(request);
        return MyApiResponse.created();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity<?> deleteById(@PathVariable Integer id) {
        productService.deleteById(id);
        return MyApiResponse.noContent();
    }


}
