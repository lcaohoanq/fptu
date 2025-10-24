package com.fpt.mss.msblindbox_se181513.domain.blindbox;

import com.fpt.mss.msblindbox_se181513.domain.category.CategoryRepository;
import com.fpt.mss.msblindbox_se181513.feign.BlindBoxFeign;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlindBoxServiceImpl  implements BlindBoxService {

    private final ProductRepository blindBoxRepository;
    private final CategoryRepository categoryRepository;
    private final BlindBoxFeign blindBoxFeign;

    @Override
    public List<BlindBoxResponse> getAll() {
        return blindBoxRepository.findAll()
            .stream()
            .map(BlindBox::toResponse)
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

        try{
            var response = blindBoxFeign.create(request);
        }catch (Exception e){
            log.error("[BlindBoxService] Error while calling BlindBoxFeign: {}", e.getMessage());
        }

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
