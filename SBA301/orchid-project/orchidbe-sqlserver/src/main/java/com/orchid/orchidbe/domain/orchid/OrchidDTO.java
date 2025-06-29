package com.orchid.orchidbe.domain.orchid;

public interface OrchidDTO {

    record OrchidReq(
        boolean isNatural,
        String description,
        String name,
        String url,
        Double price
    ) {

    }

}
