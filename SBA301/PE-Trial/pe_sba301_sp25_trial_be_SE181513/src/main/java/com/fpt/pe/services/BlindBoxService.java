package com.fpt.pe.services;

import com.fpt.pe.models.BlindBox.*;
import java.util.List;

public interface BlindBoxService {

    List<BlindBoxResponse> getAll();

    void deleteById(Integer id);

    BlindBoxResponse create(BlindBoxRequest request);

    BlindBoxResponse update(Integer id, BlindBoxRequest request);

    BlindBoxResponse updatePartial(Integer id, BlindBoxUpdateRequest request);

}
