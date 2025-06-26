package com.orchid.orchidbe.services;

import com.orchid.orchidbe.dto.RoleDTO;
import com.orchid.orchidbe.pojos.Role;
import java.util.List;

public interface RoleService {

    List<Role> getAll();
    Role getById(Long id);
    Role getByName(String name);
    void add(RoleDTO.RoleReq role);
    void update(Long id , RoleDTO.RoleReq role);
    void delete(Long id);

}
