package com.orchid.s1orchidservice.domain.role;

import com.orchid.s1orchidservice.domain.role.Role.RoleName;

public interface RoleDTO {

    record RoleReq(
        RoleName name
    ) {

    }


}
