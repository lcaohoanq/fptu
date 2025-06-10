package com.lcaohoanq.prj.controllers;

import com.lcaohoanq.prj.dto.StudentDTO;
import com.lcaohoanq.prj.pojo.Student;
import com.lcaohoanq.prj.repositories.StudentRepository;
import com.lcaohoanq.prj.services.StudentServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
@Tag(name = "Student API", description = "API for student management")
public class StudentController {

    private final StudentServiceImpl studentServiceImpl;
    private final StudentRepository studentRepository;

    @GetMapping("")
    public ResponseEntity<List<Student>> getAll() {
        return ResponseEntity.ok(studentServiceImpl.getAll());
    }

    @PostMapping("")
    public ResponseEntity<?> createStudent(StudentDTO.CreateStudentDTO student) {
        try{
            return ResponseEntity.ok(studentServiceImpl.create(student));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateStudent(
        @PathVariable Integer id,
        @Valid @RequestBody StudentDTO.UpdateStudentDTO student) {
        try {
            studentServiceImpl.update(id, student);
            return ResponseEntity.ok("Update Success");
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")

    public ResponseEntity<String> deleteStudent(@PathVariable Integer id) {

        try {
            studentServiceImpl.delete(id);
            return ResponseEntity.ok("Delete Success");
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody StudentDTO.LoginStudentDTO student) {
        try {
            var st = studentRepository.findByUsername(student.username()).orElseThrow();
            if (!st.getPass().equals(student.pass())) {
                throw new Exception("Password is incorrect");
            }
            return ResponseEntity.ok("Login Success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
