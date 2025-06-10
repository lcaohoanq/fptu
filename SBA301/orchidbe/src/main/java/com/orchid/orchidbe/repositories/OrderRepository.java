package com.orchid.orchidbe.repositories;

import com.orchid.orchidbe.pojos.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
