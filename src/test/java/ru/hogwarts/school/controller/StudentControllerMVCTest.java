package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentServiceImpl;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.hogwarts.school.controller.TestConstants.*;

@WebMvcTest(StudentController.class)
class StudentControllerMVCTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private StudentServiceImpl studentService;

    @InjectMocks
    private StudentController studentontroller;

    private final ObjectMapper mapper = new ObjectMapper();
    private final FacultyControllerMVCTest facultyControllerMVCTest=new FacultyControllerMVCTest();

    @Test
    void addStudentTest() throws Exception {
        studentConst.setFaculty(facultyConst);
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(facultyConst));
        when(studentRepository.save(any(Student.class))).thenReturn(studentConst);

        JSONObject createStudentReq = new JSONObject();

        createStudentReq.put("name",studentNameConst);
        createStudentReq.put("age",studentAgeConst);
        createStudentReq.put("faculty",new JSONObject(facultyConst.toString()));

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/student/add")
                                .content(createStudentReq.toString())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(studentNameConst))
                .andExpect(jsonPath("$.age").value(studentAgeConst))
        ;
    }

    @Test
    void getStudentTest() throws Exception {
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(studentConst));

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/student/"+studentIdConst+"/get")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(studentNameConst))
                .andExpect(jsonPath("$.age").value(studentAgeConst))
        ;
    }

    @Test
    void getStudentFacultyTest() throws Exception {
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(studentConst));
        studentConst.setFaculty(facultyConst);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/student/"+studentIdConst+"/get-faculty")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        ;
    }

    @Test
    void updateStudentTest() throws Exception {
        studentConst.setFaculty(facultyConst);
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(facultyConst));
        when(studentRepository.save(any(Student.class))).thenReturn(studentConst);

        JSONObject createStudentReq = new JSONObject();

        createStudentReq.put("id",studentIdConst);
        createStudentReq.put("name",studentNameConst);
        createStudentReq.put("age",studentAgeConst);
        createStudentReq.put("faculty",new JSONObject(facultyConst.toString()));

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/student/update")
                                .content(createStudentReq.toString())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        ;
    }

    @Test
    void deleteStudentTest() throws Exception {
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(studentConst));

        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/student/"+facultyIdConst+"/delete")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        ;
    }

    @Test
    void getStudentByAgeTest() throws Exception {
        when(studentRepository.findByAge(anyInt())).thenReturn(studentListConst);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/student/"+studentAgeConst+"/get-by-age")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(studentListConst)))
        ;
    }

    @Test
    void getStudentsBetweenAgeTest() throws Exception {
        when(studentRepository.findByAgeBetween(anyInt(),anyInt())).thenReturn(studentListConst);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/student/10/15/get-between-age")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(studentListConst)))
        ;
    }
}