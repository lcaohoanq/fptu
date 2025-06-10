package com.lcaohoanq.prj.controllers;

import com.lcaohoanq.prj.api.MyApiResponse;
import com.lcaohoanq.prj.dto.SubjectDTO;
import com.lcaohoanq.prj.pojo.Subject;
import com.lcaohoanq.prj.repositories.SubjectRepository;
import com.lcaohoanq.prj.services.SubjectServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subjects")
@Tag(name = "Subject API", description = "API for subject management")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectServiceImpl subjectServiceImpl;

    @GetMapping("")
    public ResponseEntity<MyApiResponse<?>> getAll() {
        return ResponseEntity.ok(
            MyApiResponse.builder()
                .message("Get All Success")
                .data(subjectServiceImpl.getAll())
                .build());
    }

    @PostMapping("")
    public ResponseEntity<MyApiResponse<?>> create(@RequestBody @Valid SubjectDTO.CreateSubjectDTO subject) {
        return ResponseEntity.ok(
            MyApiResponse.builder()
                .message("Create Success")
                .data(subjectServiceImpl.create(subject))
                .build());
    }

}
