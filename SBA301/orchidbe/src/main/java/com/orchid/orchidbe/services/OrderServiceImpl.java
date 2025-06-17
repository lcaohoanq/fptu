package com.orchid.orchidbe.services;

import com.orchid.orchidbe.dto.OrderDTO;
import com.orchid.orchidbe.pojos.Order;
import com.orchid.orchidbe.repositories.OrderRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public List<OrderDTO.OrderRes> getAll() {
        return orderRepository.findAll()
            .stream()
            .map(OrderDTO.OrderRes::fromEntity)
            .toList();
    }

    @Override
    public Order getById(String id) {
        return null;
    }

    @Override
    public void add(Order order) {

    }

    @Override
    public void update(Order order) {

    }

    @Override
    public void delete(int id) {

    }
}
