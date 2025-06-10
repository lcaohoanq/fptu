package com.orchid.orchidbe.dto;

public class OrderDetailDTO {

    public record OrderDetailReq(
        Integer orderId,
        Integer orchidId,
        Integer quantity,
        Double price
    ) {
    }

    public record OrderDetailRes(
        Integer id,
        Integer orderId,
        Integer orchidId,
        Integer quantity,
        Double price
    ) {
    }

}
