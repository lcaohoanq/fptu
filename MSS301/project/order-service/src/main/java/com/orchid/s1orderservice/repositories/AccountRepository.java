package com.orchid.s1orderservice.repositories;

import com.orchid.s1orderservice.domain.account.Account;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, Long id);
    Optional<Account> findByEmail(String email);


}
