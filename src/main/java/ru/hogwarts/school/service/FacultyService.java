package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface FacultyService {
    Faculty add(Faculty faculty);
    Faculty find(Long id);
    Faculty edit(Faculty faculty);
    Faculty del(Long id);
    List<Faculty> getAllByColor(String color);
    List<Faculty> getAllByNameOrColor(String name,String color);
    List<Student> getAllStudentsOfFaculty(Long id);
}
