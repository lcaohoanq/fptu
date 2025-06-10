package com.orchid.orchidbe.dto;

public class OrchidDTO {

    public record OrchidReq(
        boolean isNatural,
        String description,
        String name,
        String url,
        Double price
    ) {

    }

}
