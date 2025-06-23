package com.orchid.orchidbe.dto;

import com.orchid.orchidbe.pojos.Order;
import com.orchid.orchidbe.pojos.Order.OrderStatus;
import java.util.Date;
import org.bson.types.ObjectId;

public interface OrderDTO {

    record OrderReq(
        Double totalAmount,
        Date orderDate,
        OrderStatus orderStatus,
        String accountId
    ) {
    }

    record OrderRes(
        String id,
        Double totalAmount,
        Date orderDate,
        OrderStatus orderStatus,
        String accountId
    ) {

        public static OrderRes fromEntity(Order order) {
            return new OrderRes(
                order.getId(),
                order.getTotalAmount(),
                order.getOrderDate(),
                order.getOrderStatus(),
                order.getAccountId() != null ? order.getAccountId() : null
            );
        }
    }

}
