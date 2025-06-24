package com.orchid.orchidbe.services;

import com.orchid.orchidbe.dto.OrderDTO;
import com.orchid.orchidbe.pojos.Order;
import java.util.List;
import org.bson.types.ObjectId;

public interface OrderService {

    List<OrderDTO.OrderRes> getAll();
    Order getById(String id);
    void add(Order order);
    void update(String id, Order order);
    void delete(String id);
    List<OrderDTO.OrderRes> getByUserId(String userId);

}
