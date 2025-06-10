package com.lcaohoanq.prj.dto;

import com.lcaohoanq.prj.pojo.Student;

public interface StudentDTO {

    record LoginStudentDTO(
            String username,
            String pass
    ) {
    }

    record CreateStudentDTO(
            String username,
            String pass,
            String role
    ) {
    }

    record UpdateStudentDTO(
        String username,
        String pass,
        Student.Status status,
        String role,
        Integer subjectId
    ) {
    }

}
