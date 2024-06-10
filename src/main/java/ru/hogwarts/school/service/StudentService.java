package ru.hogwarts.school.service;

import ru.hogwarts.school.model.CountStudents;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentService {
    Student add(Student student);
    Student find(Long id);
    Student edit(Student student);
    Student del(Long id);
    List<Student> getAllByAge(Integer age);
    List<Student> findBetweenAge(Integer ageBeg,Integer ageEnd);
    Faculty findStudentFaculty(Long id);
    List<CountStudents> getStudentsCount();
    Long getStudentsAvgAge();
    List<Student> getLastFiveStudents();
    List<String> getAllLetterA();
    List<String> getAllParallel();
    List<String> getAllSync();
}
