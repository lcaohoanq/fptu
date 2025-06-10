package com.orchid.orchidbe.controllers;

import com.orchid.orchidbe.pojos.Order;
import com.orchid.orchidbe.services.AccountService;
import com.orchid.orchidbe.services.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
@Tag(name = "Order Api", description = "Operation related to Order")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("")
    public ResponseEntity<List<Order>> getOrders() {
        return ResponseEntity.ok(orderService.getAll());
    }

}
