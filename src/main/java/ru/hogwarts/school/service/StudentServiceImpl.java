package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.ItemAlreadyAddedException;
import ru.hogwarts.school.exception.ItemNotFoundException;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements  StudentService{
    Map<Long, Student> mapStudent= new HashMap<>();
    private Long counter= 1L;

    public Student add(Student student){
        Set<Student> setStudent = mapStudent.values()
                                    .stream()
                                    .filter(v->v.getAge()==student.getAge()&&v.getName().equals(student.getName()))
                                    .collect(Collectors.toSet());
        if(!setStudent.isEmpty()){
            throw new ItemAlreadyAddedException("Такой студент уже существует");
        }
        else{
            student.setId(counter);
            mapStudent.put(counter,student);
            counter++;
            return student;
        }
    }

    public Student find(Long id){
        if(!mapStudent.containsKey(id)){
            throw new ItemNotFoundException("Студент не найден");
        }
        else{
            return mapStudent.get(id);
        }
    }

    public Student edit(Long id,Student student){
        Student editStudent = find(id);
        if(mapStudent.containsValue(student)){
            throw new ItemAlreadyAddedException("Такой студент уже существует");
        }
        mapStudent.put(id,editStudent);
        return editStudent;
    }

    public Student del(Long id){
        Student delStudent = find(id);
        mapStudent.remove(id);
        return delStudent;
    }

    public Set<Student> getAllByAge(Integer age){
        return mapStudent.values()
                .stream()
                .filter(v-> v.getAge()==age)
                .collect(Collectors.toSet());
    }
}
