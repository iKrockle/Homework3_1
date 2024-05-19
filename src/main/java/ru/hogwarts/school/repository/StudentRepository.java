package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.AvgAgeStudents;
import ru.hogwarts.school.model.CountStudents;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByAge(Integer age);
    List<Student> findByNameAndAge(String name, Integer age);
    List<Student> findByAgeBetween(Integer ageBeg, Integer ageEnd);
    List<Student> findByFaculty_Id(Long id);
    @Query(value = "select count(1) from student", nativeQuery = true)
    List<CountStudents> getCountStudents();
    @Query(value = "select avg(age) from student", nativeQuery = true)
    List<AvgAgeStudents> getAvgAgeStudents();
    @Query(value = "select * from student order by id desc limit 5", nativeQuery = true)
    List<Student> getLastFiveStudents();
}
