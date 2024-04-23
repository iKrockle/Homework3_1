package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.ItemAlreadyAddedException;
import ru.hogwarts.school.exception.ItemNotFoundException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import java.util.List;

@Service
public class StudentServiceImpl implements  StudentService{
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student add(Student student){
        List<Student> facultyList = studentRepository.findByNameAndAge(student.getName(),student.getAge());
        if (facultyList.isEmpty()) {
            return studentRepository.save(student);
        }else {
            throw new ItemAlreadyAddedException();
        }
    }

    public Student find(Long id){
        return studentRepository.findById(id).orElseThrow(ItemNotFoundException::new);
    }

    public Student edit(Student student){
        List<Student> facultyList = studentRepository.findByNameAndAge(student.getName(),student.getAge());
        if (facultyList.isEmpty()) {
            return studentRepository.save(student);
        }else {
            throw new ItemAlreadyAddedException();
        }
    }

    public Student del(Long id){
        Student student = find(id);
        studentRepository.deleteById(id);
        return student;
    }

    public List<Student> getAllByAge(Integer age){
        return studentRepository.findByAge(age);
    }
}
