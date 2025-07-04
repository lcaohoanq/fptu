package com.orchid.orchidbe.domain.order;

import java.util.List;

public interface OrderService {

    List<OrderDTO.OrderRes> getAll();
    Order getById(String id);
    void add(Order order);
    void update(String id, Order order);
    void delete(String id);
    List<OrderDTO.OrderRes> getByUserId(String userId);

}
