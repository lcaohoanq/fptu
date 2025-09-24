package com.orchid.s1orchidservice.repositories;

import com.orchid.s1orchidservice.domain.orchid.Orchid;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrchidRepository extends JpaRepository<Orchid, Long> {
    boolean existsByName(String name);
}
