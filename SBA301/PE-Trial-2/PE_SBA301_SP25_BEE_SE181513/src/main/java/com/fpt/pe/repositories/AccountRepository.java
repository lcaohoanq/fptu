package com.fpt.pe.repositories;

import com.fpt.pe.models.AccountMember;
import com.fpt.pe.models.AccountMember.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountMember, Integer> {

    Optional<AccountMember> findByEmail(String Email);

    Optional<AccountMember> findByEmailAndRole(String email, Role role);

}
