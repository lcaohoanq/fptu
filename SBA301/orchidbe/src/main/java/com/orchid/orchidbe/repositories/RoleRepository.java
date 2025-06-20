package com.orchid.orchidbe.repositories;

import com.orchid.orchidbe.pojos.Role;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {

    boolean existsByName(String name);
    Optional<Role> findByName(String name);

}
