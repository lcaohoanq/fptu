package com.orchid.s1orchidservice.repositories;

import com.orchid.s1orchidservice.domain.order.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

}
