package com.fpt.pe.controllers;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fpt.pe.apis.MyApiResponse;
import com.fpt.pe.models.Car;
import com.fpt.pe.models.Country;
import com.fpt.pe.services.CarService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/infocars")
@RestController
@RequiredArgsConstructor
@Tag(name = "infocars", description = "Car API")
public class CarController {

    public record CarRequest(
        @Size(min = 11, message = "Name must be greater than 10 characters")
        @NotBlank(message = "Name is required")
        String name,

        @NotNull(message = "Price is required")
        @Min(value = 0, message = "Price must be at least 0")
        Double price,

        @NotNull(message = "Stock is required")
        @Min(value = 5, message = "Stock must be at least 5")
        @Max(value = 20, message = "Stock must be at most 20")
        Integer stock,

        @NotNull(message = "Country ID is required")
        Integer countryId
    ) {}

    // For partial updates (inline editing)
    public record CarUpdateRequest(
        @Size(min = 11, message = "Name must be greater than 10 characters")
        String name,

        @Min(value = 0, message = "Price must be at least 0")
        Double price,

        @Min(value = 1, message = "Stock must be at least 1")
        @Max(value = 100, message = "Stock must be at most 100")
        Integer stock,

        Integer countryId
    ) {}

    public record CarResponse(
        Integer id,
        String name,
        @JsonProperty(value = "stock")
        Integer unitsInStock,
        Double price,
        Country country,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Ho_Chi_Minh")
        LocalDateTime createdAt,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Ho_Chi_Minh")
        LocalDateTime updatedAt
    ) {

        public static CarResponse from(Car car) {
            return new CarResponse(
                car.getId(),
                car.getName(),
                car.getUnitsInStock(),
                car.getPrice(),
                car.getCountry(),
                car.getCreatedAt(),
                car.getUpdatedAt()
            );
        }

    }

    private final CarService carService;

    @GetMapping("")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
    })
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
    public ResponseEntity<?> findAll() {
        return MyApiResponse.success(carService.getAll());
    }

    @GetMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
    })
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        return MyApiResponse.success(carService.getById(id));
    }

    @PostMapping("")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login successful"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
    })
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
    public ResponseEntity<?> create(@Valid @RequestBody CarController.CarRequest request) {
        return MyApiResponse.success(carService.create(request));
    }

    @PutMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login successful"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
        @ApiResponse(responseCode = "404", description = "Account not found"),
    })
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
    public ResponseEntity<?> updatePartial(@PathVariable Integer id, @Valid @RequestBody CarController.CarUpdateRequest request) {
        return MyApiResponse.success(carService.updatePartial(id, request));
    }

    @DeleteMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login successful"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
        @ApiResponse(responseCode = "404", description = "Account not found"),
    })
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteById(@PathVariable Integer id) {
        carService.deleteById(id);
        return MyApiResponse.noContent();
    }


}
