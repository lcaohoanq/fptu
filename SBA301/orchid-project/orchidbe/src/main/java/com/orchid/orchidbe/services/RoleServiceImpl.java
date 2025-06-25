package com.orchid.orchidbe.services;

import com.orchid.orchidbe.dto.RoleDTO;
import com.orchid.orchidbe.pojos.Role;
import com.orchid.orchidbe.repositories.RoleRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role getById(String id) {
        return roleRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"));
    }

    @Override
    public Role getByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Role with name " + name + " not found"));
    }

    @Override
    public void add(RoleDTO.RoleReq role) {
        String newName = StringUtils.trimToNull(role.name());
        if (StringUtils.isBlank(newName)) {
            throw new IllegalArgumentException("Role name must not be empty");
        }

        if (roleRepository.existsByName(newName)) {
            throw new IllegalArgumentException("Role with name " + newName + " already exists");
        }

        roleRepository.save(new Role(newName));
    }

    @Override
    @Transactional
    public void update(String id, RoleDTO.RoleReq role) {
        var existingRole = getById(id);
        String newName = StringUtils.trimToNull(role.name());

        if (StringUtils.isNotBlank(newName) &&
            !StringUtils.equalsIgnoreCase(StringUtils.trim(existingRole.getName()), newName)) {

            log.info("Updating role with id {}: name changed from {} to {}", id, existingRole.getName(), newName);
            existingRole.setName(newName);
            roleRepository.save(existingRole);
        }
    }


    @Override
    @Transactional
    public void delete(String id) {
        var existingRole = getById(id);
        roleRepository.delete(existingRole);
    }
}
