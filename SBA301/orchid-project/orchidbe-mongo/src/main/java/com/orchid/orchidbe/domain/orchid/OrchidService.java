package com.orchid.orchidbe.domain.orchid;

import java.util.List;

public interface OrchidService {

    List<Orchid> getAll();
    Orchid getById(String id);
    void add(Orchid orchid);
    void update(String id, Orchid orchid);
    void delete(Orchid orchid);
    void deleteById(String id);

}
