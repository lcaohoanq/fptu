package com.fpt.pe.services;

import com.fpt.pe.controllers.ProductController.BlindBoxRequest;
import com.fpt.pe.controllers.ProductController.BlindBoxResponse;
import com.fpt.pe.models.BlindBox;
import com.fpt.pe.repositories.CategoryRepository;
import com.fpt.pe.repositories.ProductRepository;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class BlindBoxServiceImpl implements BlindBoxService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<BlindBoxResponse> getAll() {
        return productRepository
            .findAll()
            .stream()
            .map(BlindBoxResponse::from)
            .toList();
    }

    @Override
    public void deleteById(Integer id) {
        if (!productRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "BlindBox not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    @Override
    public BlindBoxResponse create(BlindBoxRequest request) {
        var category = categoryRepository.findById(request.categoryId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        var blindBox = BlindBox.builder()
            .name(request.name())
            .rarity(request.rarity())
            .price(request.price())
            .releaseDate(new Date()) // Current date as required
            .stock(request.stock())
            .category(category)
            .build();

        var savedBlindBox = productRepository.save(blindBox);
        return BlindBoxResponse.from(savedBlindBox);
    }
}
