package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentService {
    Student add(Student student);
    Student find(Long id);
    Student edit(Student student);
    Student del(Long id);
    List<Student> getAllByAge(Integer age);
}
