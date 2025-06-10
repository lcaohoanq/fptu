package com.lcaohoanq.prj.services;

import com.lcaohoanq.prj.dto.StudentDTO.CreateStudentDTO;
import com.lcaohoanq.prj.dto.StudentDTO.UpdateStudentDTO;
import com.lcaohoanq.prj.pojo.Student;
import com.lcaohoanq.prj.pojo.Student.Status;
import com.lcaohoanq.prj.repositories.StudentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException.NotFound;

@Service
public class StudentServiceImpl implements StudentService {


    private final StudentRepository studentRepository;
    private final SubjectServiceImpl subjectService;

    public StudentServiceImpl(StudentRepository studentRepository, SubjectServiceImpl subjectService) {
        this.studentRepository = studentRepository;
        this.subjectService = subjectService;
    }

    @Override
    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    @Override
    public Student create(CreateStudentDTO createStudentDTO) {

        var existingStudent = studentRepository.findByUsername(createStudentDTO.username());
        if (existingStudent.isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        var student = new Student();
        student.setUsername(createStudentDTO.username());
        student.setPass(createStudentDTO.pass());
        student.setStatus(Status.ACTIVE);
        student.setRole(createStudentDTO.role());

        return studentRepository.save(student);
    }

    @Override
    public void update(Integer id, UpdateStudentDTO updateStudentDTO) throws NotFoundException {

        var student = findById(id);

        student.setUsername(updateStudentDTO.username());
        student.setPass(updateStudentDTO.pass());
        student.setStatus(updateStudentDTO.status());
        student.setRole(updateStudentDTO.role());

        var subject = subjectService.getById(updateStudentDTO.subjectId());
//        student.setSubjects(subject);


        studentRepository.save(student);
    }

    @Override
    public void delete(Integer id) throws NotFoundException {
        var student = findById(id);
        if (student != null) {
            student.setStatus(Status.INACTIVE);
            studentRepository.save(student);
        }
    }

    private Student findById(Integer id) throws NotFoundException {
        return studentRepository.findById(id).orElseThrow(NotFoundException::new);
    }
}
