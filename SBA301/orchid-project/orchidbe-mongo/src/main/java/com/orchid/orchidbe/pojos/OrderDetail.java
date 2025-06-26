package com.orchid.orchidbe.pojos;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "order_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetail {

    @Id
    private String id;

    private Double price;

    private Integer quantity;

    @Column(name = "orchid_id")
    private String orchidId;

    @Column(name = "order_id")
    private String orderId;

}
