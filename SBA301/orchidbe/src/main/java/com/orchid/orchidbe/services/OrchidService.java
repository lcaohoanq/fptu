package com.orchid.orchidbe.services;

import com.orchid.orchidbe.pojos.Orchid;
import java.util.List;
import org.bson.types.ObjectId;

public interface OrchidService {

    List<Orchid> getAll();
    Orchid getById(String id);
    void add(Orchid orchid);
    void update(String id, Orchid orchid);
    void delete(Orchid orchid);

}
