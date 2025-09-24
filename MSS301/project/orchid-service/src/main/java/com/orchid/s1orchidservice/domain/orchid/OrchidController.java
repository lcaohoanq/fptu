package com.orchid.s1orchidservice.domain.orchid;

import com.orchid.s1orchidservice.apis.MyApiResponse;
import com.orchid.s1orchidservice.domain.orchid.OrchidDTO.OrchidRes;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/orchids")
@RequiredArgsConstructor
@Tag(name = "orchids", description = "Operation related to Orchid")
public class OrchidController {

    private final OrchidService orchidService;

    @GetMapping
    public ResponseEntity<?> getOrchids() {
        return ResponseEntity.ok(orchidService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrchidById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(orchidService.getById(id));
    }

    @PostMapping("")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<MyApiResponse<OrchidRes>> createOrchid(
        @Valid @RequestBody OrchidDTO.OrchidReq orchid
    ) {
        return MyApiResponse.created(orchidService.add(orchid));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<MyApiResponse<Void>> updateOrchid(
        @PathVariable("id") Long id,
        @Valid @RequestBody OrchidDTO.OrchidReq orchid
    ) {
        orchidService.update(id, orchid);
        return MyApiResponse.success();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<?> deleteOrchid(@PathVariable("id") Long id) {
        orchidService.deleteById(id);
        return MyApiResponse.success();
    }

}
