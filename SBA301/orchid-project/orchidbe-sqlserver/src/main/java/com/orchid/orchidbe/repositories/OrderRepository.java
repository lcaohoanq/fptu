package com.orchid.orchidbe.repositories;

import com.orchid.orchidbe.pojos.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByAccountId(Long accountId);

}
