package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.ItemAlreadyAddedException;
import ru.hogwarts.school.exception.ItemNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Service
public class FacultyServiceImpl implements FacultyService{
    private final Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);

    private final FacultyRepository facultyRepository;

    private final StudentRepository studentRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }

    public Faculty add(Faculty faculty){
        List<Faculty> facultyList = facultyRepository.findByNameAndColor(faculty.getName(),faculty.getColor());
        logger.info("Was invoked method for create faculty");
        if (facultyList.isEmpty()) {
            return facultyRepository.save(faculty);
        }else {
            logger.error("Faculty name:{} color:{} already exist",faculty.getName(),faculty.getColor());
            throw new ItemAlreadyAddedException();
        }
    }

    public Faculty find(Long id){
        logger.info("Was invoked method for get faculty");
        return facultyRepository.findById(id).orElseThrow(ItemNotFoundException::new);
    }

    public Faculty edit(Faculty faculty){
        List<Faculty> facultyList = facultyRepository.findByNameAndColor(faculty.getName(),faculty.getColor());
        logger.info("Was invoked method for edit faculty");
        if (facultyList.isEmpty()) {
            return facultyRepository.save(faculty);
        }else {
            logger.error("Faculty name:{} color:{} already exist",faculty.getName(),faculty.getColor());
            throw new ItemAlreadyAddedException();
        }
    }

    public Faculty del(Long id){
        Faculty faculty = find(id);
        logger.info("Was invoked method for del faculty");
        facultyRepository.deleteById(id);
        return faculty;
    }

    public List<Faculty> getAllByColor(String color){
        logger.info("Was invoked method for get faculties by color");
        return facultyRepository.findByColor(color);
    }

    public List<Faculty> getAllByNameOrColor(String name,String color){
        List<Faculty> findList = facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name,color);
        logger.info("Was invoked method for get faculties by name or color");
        if (findList.isEmpty()){
            throw new ItemNotFoundException();
        }
        else{
            logger.error("Facultys with name:{} or color:{} not found",name,color);
            return findList;
        }
    }

    public List<Student> getAllStudentsOfFaculty(Long id){
        logger.info("Was invoked method for get students by faculty");
        return studentRepository.findByFaculty_Id(id);
    }

    public List<Faculty> getAll(){
        logger.info("Was invoked method for get all students");
        return facultyRepository.findAll();
    }

    public String longestName(){
        logger.info("Was invoked method for get longest name");
        return getAll()
                .stream()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length))
                .orElseThrow(ItemNotFoundException::new);
    }

    public Integer formula(){
        logger.info("Was invoked method for get number");
        return Stream.iterate(1, a -> a +1)
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b );
    }
}
