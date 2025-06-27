package com.fpt.pe.repositories;

import com.fpt.pe.models.BlindBox;
import com.fpt.pe.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
