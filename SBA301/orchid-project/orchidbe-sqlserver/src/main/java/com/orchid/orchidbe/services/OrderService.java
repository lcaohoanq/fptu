package com.orchid.orchidbe.services;

import com.orchid.orchidbe.dto.OrderDTO;
import com.orchid.orchidbe.pojos.Order;
import java.util.List;

public interface OrderService {

    List<OrderDTO.OrderRes> getAll();
    Order getById(Long id);
    void add(Order order);
    void update(Long id, Order order);
    void delete(Long id);
    List<OrderDTO.OrderRes> getByUserId(Long userId);

}
