package com.lcaohoanq.prj.services;

import com.lcaohoanq.prj.dto.SubjectDTO;
import com.lcaohoanq.prj.pojo.Subject;
import com.lcaohoanq.prj.repositories.SubjectRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {


    private final SubjectRepository subjectRepository;

    @Override
    public List<Subject> getAll() {
        return subjectRepository.findAll();
    }

    @Override
    public Subject getById(Integer id) throws NotFoundException {
        return subjectRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public Subject create(SubjectDTO.CreateSubjectDTO subject) {
        subjectRepository.findByName(subject.name()).ifPresent(s -> {
            throw new IllegalArgumentException("Subject already exists");
        });

        var newSubject = new Subject();
        newSubject.setName(subject.name());
        newSubject.setDescription(subject.description());
        newSubject.setCode(subject.code());
        newSubject.setSemester(subject.semester());
        return subjectRepository.save(newSubject);
    }

    @Override
    public void update(Integer id, Subject subject) throws NotFoundException {

        var existingSubject = subjectRepository.findById(id).orElseThrow(NotFoundException::new);

        existingSubject.setName(subject.getName());
        existingSubject.setDescription(subject.getDescription());
        existingSubject.setCode(subject.getCode());
        existingSubject.setSemester(subject.getSemester());

        subjectRepository.save(existingSubject);

    }

    @Override
    public void delete(Integer id) throws NotFoundException {

        var subject = subjectRepository.findById(id).orElseThrow(NotFoundException::new);
        subjectRepository.delete(subject);

    }
}
