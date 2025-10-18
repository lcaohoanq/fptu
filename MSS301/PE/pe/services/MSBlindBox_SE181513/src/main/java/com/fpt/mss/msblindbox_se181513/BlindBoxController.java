package com.fpt.mss.msblindbox_se181513;

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
    public ResponseEntity<?> create(
            @Valid @RequestBody BlindBoxRequest request
    ) {
        return ResponseEntity.ok(blindBoxService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @Valid @RequestBody BlindBoxRequest request,
            @PathVariable Integer id
    ) {
        // For simplicity, reusing create method for update in this example
        // In a real scenario, you would have a separate update method in the service
        return ResponseEntity.ok(blindBoxService.update(id, request));
    }

    @DeleteMapping
    public ResponseEntity<?> delete(
            @RequestParam Integer id
    ) {
        blindBoxService.delete(id);
        return ResponseEntity.ok().build();
    }
}
