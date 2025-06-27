package com.fpt.pe.repositories;

import com.fpt.pe.models.SystemAccount;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<SystemAccount, Integer> {

    Optional<SystemAccount> findByEmail(String Email);

    Optional<SystemAccount> findByEmailAndIsActive(String Email, boolean isActive);

}
