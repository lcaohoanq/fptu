package com.fpt.mss.msblindbox_se181513;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlindBoxServiceImpl  implements BlindBoxService {

    private final ProductRepository blindBoxRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<BlindBoxResponse> getAll() {
        return blindBoxRepository.findAll().stream().map(
                blindBox -> BlindBoxResponse.builder()
                        .id(blindBox.getId())
                        .name(blindBox.getName())
                        .rarity(blindBox.getRarity())
                        .price(blindBox.getPrice())
                        .releaseDate(blindBox.getReleaseDate())
                        .stock(blindBox.getStock())
                        .categoryName(blindBox.getCategory().getName())
                        .build()
                )
                .toList();
    }

    @Override
    public BlindBoxResponse create(BlindBoxRequest request) {
        var cat = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new IllegalArgumentException("Category ID is required"));

        var blindBox = BlindBox.builder()
                .name(request.getName())
                .rarity(request.getRarity())
                .price(request.getPrice())
                .releaseDate(Date.from(Instant.now()))
                .stock(request.getStock())
                .category(cat)
                .build();
        var data = blindBoxRepository.save(blindBox);
        return BlindBox.toResponse(data);
    }

    @Override
    public BlindBoxResponse update(Integer id, BlindBoxRequest request) {
        var data = blindBoxRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Blind box not found"));
        var cat = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new IllegalArgumentException("Category ID is required"));

        var blindBox = BlindBox.builder()
                .id(data.getId())
                .name(request.getName())
                .rarity(request.getRarity())
                .price(request.getPrice())
                .releaseDate(data.getReleaseDate())
                .stock(request.getStock())
                .category(cat)
                .build();

        var updatedData = blindBoxRepository.save(blindBox);
        return BlindBox.toResponse(updatedData);
    }

    @Override
    public void delete(Integer id) {
        var data = blindBoxRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Blind box not found"));
        blindBoxRepository.delete(data);
    }
}
