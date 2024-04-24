package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping("add")
    public ResponseEntity<Faculty> addFaculty(@RequestBody Faculty student){
        Faculty createdStudent = facultyService.add(student);
        return ResponseEntity.ok(createdStudent);
    }

    @GetMapping("{id}/get")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        Faculty faculty = facultyService.find(id);
        if(faculty == null) {
            return ResponseEntity.notFound() .build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PutMapping("update")
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty user) {
        Faculty updatedUser = facultyService.edit(user);
        if(updatedUser == null) {
            return ResponseEntity.notFound() .build();
        }
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("{id}/delete")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable Long id) {
        Faculty deletedFaculty = facultyService.del(id);
        if(deletedFaculty == null) {
            return ResponseEntity.notFound() .build();
        }
        return ResponseEntity.ok(deletedFaculty);
    }

    @GetMapping("{color}/get-by-color")
    public ResponseEntity<List<Faculty>> getFacultyByColor(@PathVariable String color) {
        List<Faculty> facultyList = facultyService.getAllByColor(color);
        if(facultyList == null) {
            return ResponseEntity.notFound() .build();
        }
        return ResponseEntity.ok(facultyList);
    }

    @GetMapping("{color}/{name}/get-by-color-or-name")
    public ResponseEntity<List<Faculty>> getFacultyByColor(@RequestParam(required = false)String color, @RequestParam(required = false)String name) {
        List<Faculty> facultyList = facultyService.getAllByNameOrColor(name,color);
        if(facultyList == null) {
            return ResponseEntity.notFound() .build();
        }
        return ResponseEntity.ok(facultyList);
    }

    @GetMapping("{id}/get-students-by-faculty-id")
    public ResponseEntity<List<Student>> getAllStudentsOfFaculty(@PathVariable Long id) {
        List<Student> studentList = facultyService.getAllStudentsOfFaculty(id);
        if(studentList == null) {
            return ResponseEntity.notFound() .build();
        }
        return ResponseEntity.ok(studentList);
    }
}
