package com.fpt.pe.repositories;

import com.fpt.pe.models.BlindBox;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<BlindBox, Integer> {

}
