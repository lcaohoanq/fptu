package com.orchid.orchidbe.domain.order;

import com.orchid.orchidbe.domain.order.OrderDTO.OrderRes;
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
        return orderRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + id));
    }

    @Override
    public void add(Order order) {
        if (orderRepository.existsById(order.getId())) {
            throw new IllegalArgumentException("Order with id " + order.getId() + " already exists");
        }

        orderRepository.save(order);
    }

    @Override
    public void update(String id, Order order) {

        var existingOrder = getById(id);

        if (!existingOrder.getId().equals(order.getId())) {
            throw new IllegalArgumentException("Cannot update order with different id");
        }

        existingOrder.setOrderStatus(order.getOrderStatus());
        existingOrder.setTotalAmount(order.getTotalAmount());
        existingOrder.setOrderDate(order.getOrderDate());
        existingOrder.setAccountId(order.getAccountId());

        orderRepository.save(existingOrder);

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public List<OrderRes> getByUserId(String userId) {
        return orderRepository.findByAccountId(userId)
            .stream()
            .map(OrderDTO.OrderRes::fromEntity)
            .toList();
    }
}
