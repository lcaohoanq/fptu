package com.orchid.s1orchidservice.repositories;

import com.orchid.s1orchidservice.domain.role.Role;
import com.orchid.s1orchidservice.domain.role.Role.RoleName;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    boolean existsByName(RoleName name);
    Optional<Role> findByName(RoleName name);

}