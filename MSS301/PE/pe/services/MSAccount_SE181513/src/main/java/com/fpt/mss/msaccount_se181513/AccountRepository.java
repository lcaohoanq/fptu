package com.fpt.mss.msaccount_se181513;

import com.fpt.mss.msaccount_se181513.model.Account;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    Optional<Account> findByEmail(String email);

}
