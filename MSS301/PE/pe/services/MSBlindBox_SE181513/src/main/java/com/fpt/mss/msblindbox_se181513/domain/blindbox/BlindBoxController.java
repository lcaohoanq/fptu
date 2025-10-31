package com.fpt.mss.msblindbox_se181513.domain.blindbox;

import com.fpt.mss.annotations.RequireRole;
import com.fpt.mss.api.MyApiResponse;
import com.fpt.mss.enums.Role;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/blindboxes")
@RequiredArgsConstructor
public class BlindBoxController {

    private final BlindBoxService blindBoxService;

    @GetMapping
    public ResponseEntity<MyApiResponse<List<BlindBoxResponse>>> getAll(){
        return MyApiResponse.success(blindBoxService.getAll());
    }

    @PostMapping
    @RequireRole(Role.ADMIN)
    public ResponseEntity<?> create(
            @Valid @RequestBody BlindBoxRequest request
    ) {
        return MyApiResponse.created(blindBoxService.create(request));
    }

    @PutMapping("/{id}")
    @RequireRole(Role.ADMIN)
    public ResponseEntity<?> update(
            @Valid @RequestBody BlindBoxRequest request,
            @PathVariable Integer id
    ) {
        return MyApiResponse.success(blindBoxService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @RequireRole(Role.ADMIN)
    public ResponseEntity<?> delete(
            @PathVariable Integer id
    ) {
        blindBoxService.delete(id);
        return MyApiResponse.noContent();
    }
}
