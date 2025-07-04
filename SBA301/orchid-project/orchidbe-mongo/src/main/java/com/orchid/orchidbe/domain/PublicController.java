package com.orchid.orchidbe.domain;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/public")
@RequiredArgsConstructor
@Tag(name = "Public API", description = "Public endpoints that don't require authentication")
public class PublicController {

    // Account Controller

    // Role Controller

    // Orchid Controller

    // Order Controller

    // Category Controller

    // Token Controller

}
