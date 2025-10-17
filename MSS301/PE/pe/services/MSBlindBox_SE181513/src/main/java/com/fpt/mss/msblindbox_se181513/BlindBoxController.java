package com.fpt.mss.msblindbox_se181513;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/blindboxes")
@RequiredArgsConstructor
public class BlindBoxController {

    private final BlindBoxService blindBoxService;

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(blindBoxService.getAll());
    }

}
