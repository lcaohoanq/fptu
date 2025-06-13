package com.orchid.orchidbe.controllers;

import com.orchid.orchidbe.apis.MyApiResponse;
import com.orchid.orchidbe.dto.RoleDTO;
import com.orchid.orchidbe.pojos.Role;
import com.orchid.orchidbe.services.RoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
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
@RequestMapping("${api.prefix}/roles")
@RequiredArgsConstructor
@Tag(name = "Role Api", description = "Operation related to Role")
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<MyApiResponse<List<Role>>> getAll() {
        return MyApiResponse.success(roleService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MyApiResponse<Role>> getById(@PathVariable String id) {
        return MyApiResponse.success(roleService.getById(id));
    }

    @PostMapping
    public ResponseEntity<MyApiResponse<Object>> add(
        @Valid @RequestBody RoleDTO.RoleReq req
    ) {
        roleService.add(req);
        return MyApiResponse.created();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody RoleDTO.RoleReq req) {
        roleService.update(id, req);
        return MyApiResponse.updated();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        roleService.delete(id);
        return MyApiResponse.noContent();
    }
}