package com.orchid.s1orchidservice.repositories;

import com.orchid.s1orchidservice.domain.order.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByAccountId(Long accountId);

}
