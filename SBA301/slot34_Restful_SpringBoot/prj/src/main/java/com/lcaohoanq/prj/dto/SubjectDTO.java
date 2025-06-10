package com.lcaohoanq.prj.dto;

import jakarta.validation.constraints.NotBlank;

public interface SubjectDTO {

    record CreateSubjectDTO(
            @NotBlank(message = "Name is required") String name,
            @NotBlank(message = "Code is required") String code,
            @NotBlank(message = "Semester is required") String semester,
            String description
    ) {
    }

    record UpdateSubjectDTO(
            String name,
            String code,
            String semester,
            String description
    ) {
    }

}
