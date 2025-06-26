package com.orchid.orchidbe.services;

import com.orchid.orchidbe.dto.RoleDTO;
import com.orchid.orchidbe.pojos.Role;
import java.util.List;

public interface RoleService {

    List<Role> getAll();
    Role getById(String id);
    Role getByName(String name);
    void add(RoleDTO.RoleReq role);
    void update(String id , RoleDTO.RoleReq role);
    void delete(String id);

}
