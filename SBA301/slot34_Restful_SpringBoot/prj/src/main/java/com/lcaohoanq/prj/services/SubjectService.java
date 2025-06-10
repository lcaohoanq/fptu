package com.lcaohoanq.prj.services;

import com.lcaohoanq.prj.dto.SubjectDTO;
import com.lcaohoanq.prj.pojo.Subject;
import java.util.List;
import java.util.Optional;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

public interface SubjectService {

    List<Subject> getAll();
    Subject getById(Integer id) throws NotFoundException;
    Subject create(SubjectDTO.CreateSubjectDTO subject);
    void update(Integer id, Subject subject) throws NotFoundException;
    void delete(Integer id) throws NotFoundException;

}
