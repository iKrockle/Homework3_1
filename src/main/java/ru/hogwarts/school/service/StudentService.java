package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.Set;

public interface StudentService {
    Student add(Student student);
    Student find(Long id);
    Student edit(Long id,Student student);
    Student del(Long id);
    Set<Student> getAllByAge(Integer age);
}
