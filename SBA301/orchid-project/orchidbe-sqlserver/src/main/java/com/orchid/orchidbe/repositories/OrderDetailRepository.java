package com.orchid.orchidbe.repositories;

import com.orchid.orchidbe.domain.orchid.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

}
