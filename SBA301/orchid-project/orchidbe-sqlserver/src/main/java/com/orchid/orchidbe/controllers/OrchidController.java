package com.orchid.orchidbe.controllers;

import com.orchid.orchidbe.pojos.Orchid;
import com.orchid.orchidbe.services.OrchidService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/orchids")
@RequiredArgsConstructor
@Tag(name = "orchids", description = "Operation related to Orchid")
public class OrchidController {

    private final OrchidService orchidService;

    @GetMapping("")
    public ResponseEntity<?> getOrchids() {
        return ResponseEntity.ok(orchidService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrchidById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(orchidService.getById(id));
    }

    @PostMapping("")
    public ResponseEntity<?> createOrchid(@RequestBody Orchid orchid) {
        try {
            orchidService.add(orchid);
            return ResponseEntity.status(201).body("Orchid created successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal server error: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrchid(@PathVariable("id") Long id, @RequestBody Orchid orchid) {
        orchidService.update(id, orchid);
        return ResponseEntity.ok("Orchid updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrchid(@PathVariable("id") Long id) {
        orchidService.deleteById(id);
        return ResponseEntity.ok("Orchid deleted successfully");
    }

}
