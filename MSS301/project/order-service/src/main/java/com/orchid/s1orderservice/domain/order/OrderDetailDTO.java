package com.orchid.s1orderservice.domain.order;

public interface OrderDetailDTO {

    record OrderDetailReq(
        Integer orderId,
        Integer orchidId,
        Integer quantity,
        Double price
    ) {
    }

    record OrderDetailRes(
        Integer id,
        Integer orderId,
        Integer orchidId,
        Integer quantity,
        Double price
    ) {
    }

}
