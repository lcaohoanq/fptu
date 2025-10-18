package com.fpt.mss.msblindbox_se181513;

import java.util.List;

public interface BlindBoxService {

    List<BlindBoxResponse> getAll();
    BlindBoxResponse create(BlindBoxRequest request);
    BlindBoxResponse update(Integer id, BlindBoxRequest request);
    void delete(Integer id);
}
