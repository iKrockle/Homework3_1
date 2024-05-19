package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.hogwarts.school.controller.TestConstants.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerRESTTmplTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private StudentService studentService;

    @Autowired
    private FacultyService facultyService;

    @LocalServerPort
    private int port;

    @Test
    public void addStudentTest(){

        Faculty faculty = createMockFaculty();

        studentConst.setFaculty(faculty);

        ResponseEntity<Student> newStudentResp= testRestTemplate.postForEntity(
                "http://localhost:"+port+"/student/add",
                studentConst,
                Student.class
        );

        assertThat(newStudentResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        Student student = newStudentResp.getBody();
        assertThat(student.getName()).isEqualTo(studentConst.getName());
        assertThat(student.getAge()).isEqualTo(studentConst.getAge());
        studentService.del(student.getId());
        facultyService.del(faculty.getId());
    }

    @Test
    public void getStudentTest(){

        Faculty faculty = createMockFaculty();

        studentConst.setFaculty(faculty);
        Student studentNew = studentService.add(studentConst);

        ResponseEntity<Student> newStudentResp= testRestTemplate.getForEntity(
                "http://localhost:"+port+"/student/"+studentNew.getId()+"/get",
                Student.class
        );

        assertThat(newStudentResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        Student student = newStudentResp.getBody();
        assertThat(student.getName()).isEqualTo(studentConst.getName());
        assertThat(student.getAge()).isEqualTo(studentConst.getAge());
        studentService.del(student.getId());
        facultyService.del(faculty.getId());
    }

    @Test
    public void getStudentFacultyTest(){
        Faculty createdFaculty = facultyService.add(facultyConst);
        studentConst.setFaculty(createdFaculty);
        Student studentNew = studentService.add(studentConst);

        ResponseEntity<Student> newStudentResp= testRestTemplate.getForEntity(
                                                "http://localhost:"+port+"/student/"+studentNew.getId()+"/get-faculty",
                                                    Student.class
                                                );

        assertThat(newStudentResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        studentService.del(studentNew.getId());
        facultyService.del(createdFaculty.getId());
    }

    @Test
    public void updStudentTest(){

        Faculty createdFaculty = facultyService.add(facultyConst);
        studentConst.setFaculty(createdFaculty);
        Student studentNew = studentService.add(studentConst);

        studentNew.setName(studentNewNameConst);

        testRestTemplate.put(
                "http://localhost:"+port+"/student/update",
                studentNew,
                Student.class
        );

        ResponseEntity<Student> newStudentResp= testRestTemplate.getForEntity(
                "http://localhost:"+port+"/student/"+studentNew.getId()+"/get",
                Student.class
        );

        assertThat(newStudentResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        Student student = newStudentResp.getBody();
        assertThat(student.getName()).isEqualTo(studentNewNameConst);
        studentService.del(studentNew.getId());
        facultyService.del(createdFaculty.getId());
    }

    @Test
    public void delStudentTest(){

        Faculty createdFaculty = facultyService.add(facultyConst);
        studentConst.setFaculty(createdFaculty);
        Student studentNew = studentService.add(studentConst);

        testRestTemplate.delete(
                "http://localhost:"+port+"/student/"+studentNew.getId()+"/delete",
                Student.class
        );

        ResponseEntity<Student> newStudentResp= testRestTemplate.getForEntity(
                "http://localhost:"+port+"/student/"+studentNew.getId()+"/get",
                Student.class
        );

        assertThat(newStudentResp.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        facultyService.del(createdFaculty.getId());
    }

    @Test
    public void getStudentByAgeTest(){

        Faculty faculty = createMockFaculty();
        studentConst.setFaculty(faculty);
        Student firstStudent = studentService.add(studentConst);
        Student scndStudent = createMockStudent(studentNewNameConst,studentAgeConst+1,faculty);


        ResponseEntity<List<Student>> newStudentResp= testRestTemplate.exchange(
                "http://localhost:" + port + "/student/" + studentAgeConst + "/get-by-age",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );

        assertThat(newStudentResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<Student> student = newStudentResp.getBody();
        assertThat(student.size()).isEqualTo(1);

        studentService.del(firstStudent.getId());
        studentService.del(scndStudent.getId());
        facultyService.del(faculty.getId());
    }

    @Test
    public void getStudentBetweenAgeTest(){

        Faculty faculty = createMockFaculty();
        studentConst.setFaculty(faculty);
        Student firstStudent = studentService.add(studentConst);
        Student scndStudent = createMockStudent(studentNewNameConst,studentAgeConst+1,faculty);


        ResponseEntity<List<Student>> newStudentResp= testRestTemplate.exchange(
                "http://localhost:" + port + "/student/" + studentAgeConst + "/"+studentAgeConst+1+"/get-between-age",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );

        assertThat(newStudentResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<Student> student = newStudentResp.getBody();
        assertThat(student.size()).isEqualTo(2);
        studentService.del(firstStudent.getId());
        studentService.del(scndStudent.getId());
        facultyService.del(faculty.getId());
    }

    public Faculty createMockFaculty(){
        return facultyService.add(facultyConst);
    }

    public Student createMockStudent( String name,Integer age,Faculty faculty){
        return studentService.add(new Student(null,name,age,faculty));
    }
}