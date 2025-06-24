package com.orchid.orchidbe.repositories;

import com.orchid.orchidbe.pojos.Order;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {

    List<Order> findByAccountId(String accountId);

}
