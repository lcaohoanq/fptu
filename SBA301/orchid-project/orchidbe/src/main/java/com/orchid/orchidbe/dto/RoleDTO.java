package com.orchid.orchidbe.dto;

import jakarta.validation.constraints.NotNull;

public interface RoleDTO {

    @NotNull(message = "Name is not blank")
    record RoleReq(
        String name
    ) {

    }


}
