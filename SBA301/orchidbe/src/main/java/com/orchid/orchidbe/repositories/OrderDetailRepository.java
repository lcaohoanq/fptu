package com.orchid.orchidbe.repositories;

import com.orchid.orchidbe.pojos.OrderDetail;
import org.bson.types.ObjectId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderDetailRepository extends MongoRepository<OrderDetail, String> {

}
