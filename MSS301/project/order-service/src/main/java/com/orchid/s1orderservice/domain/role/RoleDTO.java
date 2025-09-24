package com.orchid.s1orderservice.domain.role;

import com.orchid.s1orderservice.domain.role.Role.RoleName;

public interface RoleDTO {

    record RoleReq(
        RoleName name
    ) {

    }


}
