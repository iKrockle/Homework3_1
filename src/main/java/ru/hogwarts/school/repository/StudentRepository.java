package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByAge(Integer age);
    List<Student> findByNameAndAge(String name, Integer age);
    List<Student> findByAgeBetween(Integer ageBeg, Integer ageEnd);
    List<Student> findByFaculty_id(Long facultyId);
}
