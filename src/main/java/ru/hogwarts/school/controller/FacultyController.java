package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Set;

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
    public ResponseEntity<Faculty> updateFaculty(@PathVariable Long id, @RequestBody Faculty user) {
        Faculty updatedUser = facultyService.edit(id, user);
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
    public ResponseEntity<Set<Faculty>> getFacultyByColor(@PathVariable String color) {
        Set<Faculty> facultySet = facultyService.getAllByColor(color);
        if(facultySet == null) {
            return ResponseEntity.notFound() .build();
        }
        return ResponseEntity.ok(facultySet);
    }
}
