package com.orchid.orchidbe.controllers;

import com.orchid.orchidbe.apis.MyApiResponse;
import com.orchid.orchidbe.dto.OrderDTO;
import com.orchid.orchidbe.pojos.Order;
import com.orchid.orchidbe.services.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
@Tag(name = "Order Api", description = "Operation related to Order")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER', 'ROLE_STAFF')")
    public ResponseEntity<MyApiResponse<List<OrderDTO.OrderRes>>> getOrders() {
        return MyApiResponse.success(orderService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MyApiResponse<Order>> getOrderById(@PathVariable String id) {
        return MyApiResponse.success(orderService.getById(id));
    }

    @PostMapping
    public ResponseEntity<MyApiResponse<Object>> createOrder(
        @RequestBody @Valid Order orderReq
    ) {
        orderService.add(orderReq);
        return MyApiResponse.created();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MyApiResponse<Object>> updateOrder(
        @PathVariable String id, @RequestBody Order orderReq
    ) {
        orderService.update(id, orderReq);
        return MyApiResponse.updated();
    }


}
