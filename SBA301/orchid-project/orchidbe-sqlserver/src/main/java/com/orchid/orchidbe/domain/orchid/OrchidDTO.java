package com.orchid.orchidbe.domain.orchid;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public interface OrchidDTO {

    record OrchidReq(
        boolean isNatural,
        String description,

        @NotBlank(message = "Name is not blank")
        String name,
        String url,

        @Min(value = 0, message = "Price must be greater than or equal to 0")
        @Max(value = 100000000, message = "Price must be less than or equal to 100,000,000")
        Double price
    ) {

    }

    record OrchidRes(
        Long id,
        boolean isNatural,
        String description,
        String name,
        String url,
        Double price,
        Long categoryId
    ) {

    }

}
