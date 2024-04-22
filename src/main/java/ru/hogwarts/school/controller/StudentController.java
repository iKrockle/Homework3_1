package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

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
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        Student updatedUser = studentService.edit(student);
        if(updatedUser == null) {
            return ResponseEntity.notFound() .build();
        }
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("{id}/delete")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id) {
        Student deletedStudent = studentService.del(id);
        if(deletedStudent == null) {
            return ResponseEntity.notFound() .build();
        }
        return ResponseEntity.ok(deletedStudent);
    }

    @GetMapping("{age}/get-by-age")
    public ResponseEntity<List<Student>> getFacultyByColor(@PathVariable Integer age) {
        List<Student> facultyList = studentService.getAllByAge(age);
        if(facultyList == null) {
            return ResponseEntity.notFound() .build();
        }
        return ResponseEntity.ok(facultyList);
    }
}
