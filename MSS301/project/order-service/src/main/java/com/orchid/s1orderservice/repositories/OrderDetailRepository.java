package com.orchid.s1orderservice.repositories;

import com.orchid.s1orderservice.domain.order.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

}
