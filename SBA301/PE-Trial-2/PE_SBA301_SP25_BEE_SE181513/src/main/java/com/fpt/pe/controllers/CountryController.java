package com.fpt.pe.controllers;

import com.fpt.pe.apis.MyApiResponse;
import com.fpt.pe.services.CountryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/countries")
@RestController
@RequiredArgsConstructor
@Tag(name = "countries", description = "Country API")
public class CountryController {

    private final CountryService countryService;

    @GetMapping("")
    public ResponseEntity<?> getAll(){
        return MyApiResponse.success(countryService.getAll());
    }

}
