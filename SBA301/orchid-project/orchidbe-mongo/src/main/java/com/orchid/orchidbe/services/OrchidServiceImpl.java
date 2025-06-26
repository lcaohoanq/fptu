package com.orchid.orchidbe.services;

import com.orchid.orchidbe.pojos.Orchid;
import com.orchid.orchidbe.repositories.OrchidRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrchidServiceImpl implements OrchidService  {

    private final OrchidRepository orchidRepository;

    @Override
    public List<Orchid> getAll() {
        return orchidRepository.findAll();
    }

    @Override
    public Orchid getById(String id) {
        return orchidRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orchid not found with id: " + id));
    }

    @Override
    public void add(Orchid orchid) {
        if (orchidRepository.existsByName(orchid.getName())) {
            throw new IllegalArgumentException("Orchid with this name already exists");
        }
        orchidRepository.save(orchid);
    }

    @Override
    public void update(String id, Orchid orchid) {

        var existingOrchid = getById(id);

        existingOrchid.setNatural(orchid.isNatural());
        existingOrchid.setDescription(orchid.getDescription());
        existingOrchid.setName(orchid.getName());
        existingOrchid.setUrl(orchid.getUrl());
        existingOrchid.setPrice(orchid.getPrice());

        orchidRepository.save(existingOrchid);
    }

    @Override
    public void delete(Orchid orchid) {
        var existingOrchid = getById(orchid.getId());
        orchidRepository.delete(existingOrchid);
    }

    @Override
    public void deleteById(String id) {
        var existingOrchid = getById(id);
        orchidRepository.delete(existingOrchid);
    }
}
