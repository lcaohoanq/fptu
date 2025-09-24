package com.orchid.s1accountservice.domain.role;

import com.orchid.s1accountservice.domain.role.Role.RoleName;

public interface RoleDTO {

    record RoleReq(
        RoleName name
    ) {

    }


}
