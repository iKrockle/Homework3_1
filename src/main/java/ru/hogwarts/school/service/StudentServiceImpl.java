package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.ItemAlreadyAddedException;
import ru.hogwarts.school.exception.ItemNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import java.util.List;

@Service
public class StudentServiceImpl implements  StudentService{
    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;

    public StudentServiceImpl(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }

    public Student add(Student student){
        List<Student> studentList = studentRepository.findByNameAndAge(student.getName(),student.getAge());
        facultyRepository.findById(student.getFaculty().getId()).orElseThrow(ItemNotFoundException::new);
        if (studentList.isEmpty()) {
            return studentRepository.save(student);
        }else {
            throw new ItemAlreadyAddedException();
        }
    }

    public Student find(Long id){
        return studentRepository.findById(id).orElseThrow(ItemNotFoundException::new);
    }

    public Student edit(Student student){
        List<Student> studentList = studentRepository.findByNameAndAge(student.getName(),student.getAge());
        facultyRepository.findById(student.getFaculty().getId()).orElseThrow(ItemNotFoundException::new);
        if (studentList.isEmpty()) {
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

    public List<Student> findBetweenAge(Integer ageBeg,Integer ageEnd){
        List<Student> findList = studentRepository.findByAgeBetween(ageBeg,ageEnd);
        if (findList.isEmpty()){
            throw new ItemNotFoundException();
        }
        else{
            return findList;
        }
    }

    public Faculty findStudentFaculty(Long id){
        return find(id).getFaculty();
    }
}
