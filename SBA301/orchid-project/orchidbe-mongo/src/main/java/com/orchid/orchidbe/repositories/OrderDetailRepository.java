package com.orchid.orchidbe.repositories;

import com.orchid.orchidbe.pojos.OrderDetail;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderDetailRepository extends MongoRepository<OrderDetail, String> {

}
