package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.ItemAlreadyAddedException;
import ru.hogwarts.school.exception.ItemNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.List;

@Service
public class FacultyServiceImpl implements FacultyService{
    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
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
        return find(id).getStudentList();
    }
}
