package com.orchid.orchidbe.services;

import com.orchid.orchidbe.pojos.Orchid;
import java.util.List;

public interface OrchidService {

    List<Orchid> getAll();
    Orchid getById(Long id);
    void add(Orchid orchid);
    void update(Long id, Orchid orchid);
    void delete(Orchid orchid);
    void deleteById(Long id);

}
