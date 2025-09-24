package com.orchid.s1orderservice.repositories;

import com.orchid.s1orderservice.domain.orchid.Orchid;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrchidRepository extends JpaRepository<Orchid, Long> {
    boolean existsByName(String name);
}
