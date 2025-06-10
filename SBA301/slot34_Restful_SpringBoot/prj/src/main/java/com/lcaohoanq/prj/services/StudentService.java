package com.lcaohoanq.prj.services;

import com.lcaohoanq.prj.dto.StudentDTO;
import com.lcaohoanq.prj.dto.StudentDTO.UpdateStudentDTO;
import com.lcaohoanq.prj.pojo.Student;
import com.lcaohoanq.prj.pojo.Subject;
import java.util.List;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

public interface StudentService {

    List<Student> getAll();
    Student create(StudentDTO.CreateStudentDTO createStudentDTO);
    void update(Integer id, StudentDTO.UpdateStudentDTO updateStudentDTO) throws NotFoundException;
    void delete(Integer id) throws NotFoundException;

}
