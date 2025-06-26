package com.orchid.orchidbe.services;

import com.orchid.orchidbe.dto.CategoryDTO;
import com.orchid.orchidbe.pojos.Category;
import java.util.List;

public interface CategoryService {

    List<Category> getAll();
    Category getById(Long id);
    void save(CategoryDTO.CategoryReq category);
    void update(Long id, CategoryDTO.CategoryReq category);
    void delete(Long id);


}
