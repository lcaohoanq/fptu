package com.orchid.orchidbe.repositories;

import com.orchid.orchidbe.pojos.Account;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account, String> {

    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, String id);
    Optional<Account> findByEmail(String email);


}
