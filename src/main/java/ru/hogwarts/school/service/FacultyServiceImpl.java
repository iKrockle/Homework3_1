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
public class FacultyServiceImpl implements FacultyService{
    private final FacultyRepository facultyRepository;

    private final StudentRepository studentRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }

    public Faculty add(Faculty faculty){
        List<Faculty> facultyList = facultyRepository.findByNameAndColor(faculty.getName(),faculty.getColor());
        if (facultyList.isEmpty()) {
            return facultyRepository.save(faculty);
        }else {
            throw new ItemAlreadyAddedException();
        }
    }

    public Faculty find(Long id){
        return facultyRepository.findById(id).orElseThrow(ItemNotFoundException::new);
    }

    public Faculty edit(Faculty faculty){
        List<Faculty> facultyList = facultyRepository.findByNameAndColor(faculty.getName(),faculty.getColor());
        if (facultyList.isEmpty()) {
            return facultyRepository.save(faculty);
        }else {
            throw new ItemAlreadyAddedException();
        }
    }

    public Faculty del(Long id){
        Faculty faculty = find(id);
        facultyRepository.deleteById(id);
        return faculty;
    }

    public List<Faculty> getAllByColor(String color){
        return facultyRepository.findByColor(color);
    }

    public List<Faculty> getAllByNameOrColor(String name,String color){
        List<Faculty> findList = facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name,color);
        if (findList.isEmpty()){
            throw new ItemNotFoundException();
        }
        else{
            return findList;
        }
    }

    public List<Student> getAllStudentsOfFaculty(Long id){
        return studentRepository.findByFaculty_Id(id);
    }
}
