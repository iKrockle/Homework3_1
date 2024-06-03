package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.ItemAlreadyAddedException;
import ru.hogwarts.school.exception.ItemNotFoundException;
import ru.hogwarts.school.model.AvgAgeStudents;
import ru.hogwarts.school.model.CountStudents;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import java.util.List;

@Service
public class StudentServiceImpl implements  StudentService{
    Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;

    public StudentServiceImpl(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }

    public Student add(Student student){
        List<Student> studentList = studentRepository.findByNameAndAge(student.getName(),student.getAge());
        facultyRepository.findById(student.getFaculty().getId()).orElseThrow(ItemNotFoundException::new);
        logger.info("Was invoked method for create student");
        if (studentList.isEmpty()) {
            return studentRepository.save(student);
        }else {
            logger.error("Student name:{} age:{} already exist",student.getName(),student.getAge());
            throw new ItemAlreadyAddedException();
        }
    }

    public Student find(Long id){
        logger.info("Was invoked method for get student");
        return studentRepository.findById(id).orElseThrow(ItemNotFoundException::new);
    }

    public Student edit(Student student){
        List<Student> studentList = studentRepository.findByNameAndAge(student.getName(),student.getAge());
        logger.info("Was invoked method for edit student");
        facultyRepository.findById(student.getFaculty().getId()).orElseThrow(ItemNotFoundException::new);
        if (studentList.isEmpty()) {
            return studentRepository.save(student);
        }else {
            logger.error("Student name:{} age:{} already exist",student.getName(),student.getAge());
            throw new ItemAlreadyAddedException();
        }
    }

    public Student del(Long id){
        logger.info("Was invoked method for del student");
        Student student = find(id);
        studentRepository.deleteById(id);
        return student;
    }

    public List<Student> getAllByAge(Integer age){
        logger.info("Was invoked method for get all student");
        return studentRepository.findByAge(age);
    }

    public List<Student> findBetweenAge(Integer ageBeg,Integer ageEnd){
        List<Student> findList = studentRepository.findByAgeBetween(ageBeg,ageEnd);
        logger.info("Was invoked method for find student between age");
        if (findList.isEmpty()){
            logger.error("Students between age {} and {} not found",ageBeg,ageEnd);
            throw new ItemNotFoundException();
        }
        else{
            return findList;
        }
    }

    public Faculty findStudentFaculty(Long id){
        logger.info("Was invoked method for find student faculty");
        return find(id).getFaculty();
    }

    public List<CountStudents> getStudentsCount(){
        logger.info("Was invoked method for get student count");
        return  studentRepository.getCountStudents();
    }

    public List<AvgAgeStudents> getStudentsAvgAge(){
        logger.info("Was invoked method for get student avg age");
        return  studentRepository.getAvgAgeStudents();
    }

    public List<Student> getLastFiveStudents(){
        logger.info("Was invoked method for get last 5 students");
        return  studentRepository.getLastFiveStudents();
    }
}
