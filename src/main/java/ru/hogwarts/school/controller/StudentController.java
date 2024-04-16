package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Set;

@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("add")
    public ResponseEntity<Student> addStudent(@RequestBody Student student){
        Student createdStudent = studentService.add(student);
        return ResponseEntity.ok(createdStudent);
    }

    @GetMapping("{id}/get")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Student student = studentService.find(id);
        if(student == null) {
            return ResponseEntity.notFound() .build();
        }
        return ResponseEntity.ok(student);
    }

    @PutMapping("update")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student user) {
        Student updatedUser = studentService.edit(id, user);
        if(updatedUser == null) {
            return ResponseEntity.notFound() .build();
        }
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("{id}/delete")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id) {
        Student deletedStudent = studentService.del(id);
        if(deletedStudent == null) {
            return ResponseEntity.notFound() .build();
        }
        return ResponseEntity.ok(deletedStudent);
    }

    @GetMapping("{age}/get-by-age")
    public ResponseEntity<Set<Student>> getFacultyByColor(@PathVariable Integer age) {
        Set<Student> facultySet = studentService.getAllByAge(age);
        if(facultySet == null) {
            return ResponseEntity.notFound() .build();
        }
        return ResponseEntity.ok(facultySet);
    }
}
