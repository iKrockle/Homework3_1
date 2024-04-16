package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.ItemAlreadyAddedException;
import ru.hogwarts.school.exception.ItemNotFoundException;
import ru.hogwarts.school.model.Faculty;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService{
    Map<Long, Faculty> mapFaculty= new HashMap<>();
    private Long counter= 1L;

    public Faculty add(Faculty faculty){
        Set<Faculty> setFaculty = mapFaculty.values()
                .stream()
                .filter(v->v.getColor()==faculty.getColor()&&v.getName().equals(faculty.getName()))
                .collect(Collectors.toSet());
        if(!setFaculty.isEmpty()){
            throw new ItemAlreadyAddedException("Такой факультет уже существует");
        }
        else{
            faculty.setId(counter);
            mapFaculty.put(counter,faculty);
            counter++;
            return faculty;
        }
    }

    public Faculty find(Long id){
        if(!mapFaculty.containsKey(id)){
            throw new ItemNotFoundException("Факультет не найден");
        }
        else{
            return mapFaculty.get(id);
        }
    }

    public Faculty edit(Long id, Faculty faculty){
        if(mapFaculty.containsValue(faculty)){
            throw new ItemAlreadyAddedException("Такой факультет уже существует");
        }
        mapFaculty.put(id,faculty);
        return faculty;
    }

    public Faculty del(Long id){
        Faculty delFaculty = find(id);
        mapFaculty.remove(id);
        return delFaculty;
    }

    public Set<Faculty> getAllByColor(String color){
        return mapFaculty.values()
                    .stream()
                    .filter(v-> v.getColor().equals(color))
                    .collect(Collectors.toSet());
    }
}
