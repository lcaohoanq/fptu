package com.fpt.pe.services;

import com.fpt.pe.models.BlindBox;
import com.fpt.pe.models.BlindBox.BlindBoxRequest;
import com.fpt.pe.models.BlindBox.BlindBoxResponse;
import com.fpt.pe.models.BlindBox.BlindBoxUpdateRequest;
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

    @Override
    public BlindBoxResponse update(Integer id, BlindBoxRequest request) {
        var existingBlindBox = productRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "BlindBox not found with id: " + id));

        var category = categoryRepository.findById(request.categoryId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        // Update the existing blind box
        existingBlindBox.setName(request.name());
        existingBlindBox.setRarity(request.rarity());
        existingBlindBox.setPrice(request.price());
        existingBlindBox.setStock(request.stock());
        existingBlindBox.setCategory(category);
        // Note: We don't update releaseDate as it should remain the original creation date

        var updatedBlindBox = productRepository.save(existingBlindBox);
        return BlindBoxResponse.from(updatedBlindBox);
    }

    @Override
    public BlindBoxResponse updatePartial(Integer id, BlindBoxUpdateRequest request) {
        var existingBlindBox = productRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "BlindBox not found with id: " + id));

        // Update only the provided fields
        if (request.name() != null) {
            existingBlindBox.setName(request.name());
        }
        if (request.rarity() != null) {
            existingBlindBox.setRarity(request.rarity());
        }
        if (request.price() != null) {
            existingBlindBox.setPrice(request.price());
        }
        if (request.stock() != null) {
            existingBlindBox.setStock(request.stock());
        }
        if (request.categoryId() != null) {
            var category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
            existingBlindBox.setCategory(category);
        }

        var updatedBlindBox = productRepository.save(existingBlindBox);
        return BlindBoxResponse.from(updatedBlindBox);
    }
}
