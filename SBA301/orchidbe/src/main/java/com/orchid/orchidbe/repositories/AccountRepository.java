package com.orchid.orchidbe.repositories;

import com.orchid.orchidbe.pojos.Account;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, int id);
    Optional<Account> findByEmail(String email);


}
