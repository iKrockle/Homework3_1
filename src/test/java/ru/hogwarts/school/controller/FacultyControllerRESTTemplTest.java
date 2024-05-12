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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static ru.hogwarts.school.controller.TestConstants.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FacultyControllerRESTTemplTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private StudentService studentService;

    @Autowired
    private FacultyService facultyService;

    @LocalServerPort
    private int port;

    @Test
    void addFaculty() {
        ResponseEntity<Faculty> newFacultyResp= testRestTemplate.postForEntity(
                "http://localhost:"+port+"/faculty/add",
                facultyConst,
                Faculty.class
        );

        assertThat(newFacultyResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        Faculty faculty = newFacultyResp.getBody();
        assertThat(faculty.getName()).isEqualTo(facultyConst.getName());
        assertThat(faculty.getColor()).isEqualTo(facultyConst.getColor());
        facultyService.del(faculty.getId());
    }

    @Test
    void getFaculty() {
        Faculty newFaculty = createMockFaculty();
        ResponseEntity<Faculty> newFacultyResp= testRestTemplate.getForEntity(
                "http://localhost:"+port+"/faculty/"+newFaculty.getId()+"/get",
                Faculty.class
        );

        assertThat(newFacultyResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        Faculty faculty = newFacultyResp.getBody();
        assertThat(faculty.getName()).isEqualTo(facultyConst.getName());
        assertThat(faculty.getColor()).isEqualTo(facultyConst.getColor());
        facultyService.del(newFaculty.getId());
    }

    @Test
    void updateFaculty() {
        Faculty newFaculty = createMockFaculty();
        newFaculty.setName(facultyNewNameConst);
        testRestTemplate.put(
                "http://localhost:"+port+"/faculty/update",
                newFaculty,
                Faculty.class
        );

        ResponseEntity<Faculty> newFacultyResp= testRestTemplate.getForEntity(
                "http://localhost:"+port+"/faculty/"+newFaculty.getId()+"/get",
                Faculty.class
        );

        assertThat(newFacultyResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        Faculty faculty = newFacultyResp.getBody();
        assertThat(faculty.getName()).isEqualTo(facultyNewNameConst);
        facultyService.del(newFaculty.getId());
    }

    @Test
    void deleteFaculty() {
        Faculty newFaculty = createMockFaculty();

        testRestTemplate.delete(
                "http://localhost:"+port+"/faculty/"+newFaculty.getId()+"/delete",
                Faculty.class
        );

        ResponseEntity<Faculty> newFacultyResp= testRestTemplate.getForEntity(
                "http://localhost:"+port+"/faculty/"+newFaculty.getId()+"/get",
                Faculty.class
        );

        assertThat(newFacultyResp.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void getFacultyByColor() {
        createMockFaculty("Hogwarts","Red");
        createMockFaculty("Slizerin","Green");

        ResponseEntity<List<Faculty>> newFacultyResp= testRestTemplate.exchange(
                "http://localhost:"+port+"/faculty/Red/get-by-color",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>(){}
        );

        assertThat(newFacultyResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<Faculty> faculty = newFacultyResp.getBody();
        assertThat(faculty.size()).isEqualTo(1);
    }

    @Test
    void getFacultyByNameOrColor() {
        createMockFaculty("Hogwarts","Red");
        createMockFaculty("Slizerin","Green");

        ResponseEntity<List<Faculty>> newFacultyResp= testRestTemplate.exchange(
                "http://localhost:"+port+"/faculty/get-by-color-or-name?color=Red&name=Slizerin",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>(){}
        );

        assertThat(newFacultyResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<Faculty> faculty = newFacultyResp.getBody();
        assertThat(faculty.size()).isEqualTo(2);
    }

    @Test
    void getAllStudentsOfFaculty() {
        Faculty createdFaculty = createMockFaculty();
        studentConst.setFaculty(createdFaculty);
        Student studentNew = studentService.add(studentConst);

        ResponseEntity<List<Student>> newFacultyResp= testRestTemplate.exchange(
                "http://localhost:"+port+"/faculty/"+createdFaculty.getId()+"/get-students-by-faculty-id",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>(){}
        );

        assertThat(newFacultyResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<Student> student = newFacultyResp.getBody();
        assertFalse(student.isEmpty());
        studentService.del(studentNew.getId());
        facultyService.del(createdFaculty.getId());
    }

    public Faculty createMockFaculty(){
        return facultyService.add(facultyConst);
    }

    public Faculty createMockFaculty(String name,String color){
        return facultyService.add(new Faculty(null,name,color));
    }
}