package com.lcaohoanq.prj.repositories;

import com.lcaohoanq.prj.pojo.Student;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    Optional<Student> findByUsername(String username);

}
