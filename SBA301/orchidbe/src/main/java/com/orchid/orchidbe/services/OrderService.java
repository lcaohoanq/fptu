package com.orchid.orchidbe.services;

import com.orchid.orchidbe.dto.OrderDTO;
import com.orchid.orchidbe.pojos.Order;
import java.util.List;

public interface OrderService {

    List<OrderDTO.OrderRes> getAll();
    Order getById(int id);
    void add(Order order);
    void update(Order order);
    void delete(int id);

}
