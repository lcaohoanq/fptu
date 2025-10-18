package com.fpt.mss.msblindbox_se181513;

import com.fpt.mss.msblindbox_se181513.annotations.RequireRole;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/blindboxes")
@RequiredArgsConstructor
public class BlindBoxController {

    private final BlindBoxService blindBoxService;

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(blindBoxService.getAll());
    }

    @PostMapping
    @RequireRole(Role.ADMIN)
    public ResponseEntity<?> create(
            @Valid @RequestBody BlindBoxRequest request
    ) {
        return ResponseEntity.ok(blindBoxService.create(request));
    }

    @PutMapping("/{id}")
    @RequireRole(Role.ADMIN)
    public ResponseEntity<?> update(
            @Valid @RequestBody BlindBoxRequest request,
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(blindBoxService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @RequireRole(Role.ADMIN)
    public ResponseEntity<?> delete(
            @PathVariable Integer id
    ) {
        blindBoxService.delete(id);
        return ResponseEntity.ok().build();
    }
}
