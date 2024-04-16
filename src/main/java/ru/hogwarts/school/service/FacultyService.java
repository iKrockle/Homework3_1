package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.Set;

public interface FacultyService {
    Faculty add(Faculty faculty);
    Faculty find(Long id);
    Faculty edit(Long id,Faculty faculty);
    Faculty del(Long id);
    Set<Faculty> getAllByColor(String color);
}
