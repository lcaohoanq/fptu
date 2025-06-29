package com.orchid.orchidbe.domain.role;

import com.orchid.orchidbe.domain.role.Role.RoleName;
import jakarta.validation.constraints.NotNull;

public interface RoleDTO {

    @NotNull(message = "Name is not blank")
    record RoleReq(
        RoleName name
    ) {

    }


}
