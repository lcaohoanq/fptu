package com.orchid.s1orderservice.domain.orchid;

import com.orchid.s1orderservice.domain.orchid.OrchidDTO.OrchidReq;
import java.util.List;

public interface OrchidService {

    List<OrchidDTO.OrchidRes> getAll();
    OrchidDTO.OrchidRes getById(Long id);
    OrchidDTO.OrchidRes add(OrchidDTO.OrchidReq orchid);
    void update(Long id, OrchidReq orchid);
    void deleteById(Long id);

}
