package com.orchid.orchidbe.domain.order;

import java.util.List;

public interface OrderService {

    List<OrderDTO.OrderRes> getAll();
    Order getById(Long id);
    void add(Order order);
    void update(Long id, Order order);
    void delete(Long id);
    List<OrderDTO.OrderRes> getByUserId(Long userId);

}
