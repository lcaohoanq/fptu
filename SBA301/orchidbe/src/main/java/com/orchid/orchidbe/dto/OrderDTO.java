package com.orchid.orchidbe.dto;

import com.orchid.orchidbe.pojos.Order.OrderStatus;
import java.util.Date;

public class OrderDTO {

    public record OrderReq(
        Double totalAmount,
        Date orderDate,
        OrderStatus orderStatus,
        Integer accountId
    ) {
    }

    public record OrderRes(
        Integer id,
        Double totalAmount,
        Date orderDate,
        OrderStatus orderStatus,
        Integer accountId
    ) {
    }

}
