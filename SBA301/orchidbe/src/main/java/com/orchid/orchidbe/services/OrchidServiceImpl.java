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
    public Orchid getById(int id) {
        return null;
    }

    @Override
    public void add(Orchid orchid) {

    }

    @Override
    public void update(Orchid orchid) {

    }

    @Override
    public void delete(Orchid orchid) {

    }
}
