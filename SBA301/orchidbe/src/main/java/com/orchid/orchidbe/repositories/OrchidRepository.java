package com.orchid.orchidbe.repositories;

import com.orchid.orchidbe.pojos.Orchid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrchidRepository extends JpaRepository<Orchid, Integer> {
}
