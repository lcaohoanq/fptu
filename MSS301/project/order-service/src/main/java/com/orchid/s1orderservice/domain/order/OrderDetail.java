package com.orchid.s1orderservice.domain.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.orchid.s1orderservice.domain.orchid.Orchid;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "order_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique=true, nullable=false)
    @JsonProperty("id")
    private Long id;

    private Double price;

    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "orchid_id", referencedColumnName = "id")
    private Orchid orchidId;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

}
