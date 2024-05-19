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
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.FacultyServiceImpl;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.hogwarts.school.controller.TestConstants.*;

@WebMvcTest(controllers = FacultyController.class)
class FacultyControllerMVCTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepository facultyRepository;

    @MockBean
    private StudentRepository studentRepository;

    @SpyBean
    private FacultyServiceImpl facultyService;

    @InjectMocks
    private FacultyController facultyController;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void addFacultyTest() throws Exception {
        when(facultyRepository.save(any(Faculty.class))).thenReturn(facultyConst);

        JSONObject createFacultyReq = new JSONObject();

        createFacultyReq.put("name",facultyNameConst);
        createFacultyReq.put("color",facultyColorConst);


        mockMvc.perform(
                MockMvcRequestBuilders.post("/faculty/add")
                        .content(createFacultyReq.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(facultyNameConst))
                .andExpect(jsonPath("$.color").value(facultyColorConst))
        ;
    }

    @Test
    public void getFacultyTest() throws Exception {
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(facultyConst));

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/faculty/"+facultyIdConst+"/get")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(facultyNameConst))
                .andExpect(jsonPath("$.color").value(facultyColorConst))
        ;
    }

    @Test
    public void updateFacultyTest() throws Exception {
        when(facultyRepository.save(any(Faculty.class))).thenReturn(facultyConst);

        JSONObject updateFacultyReq = new JSONObject();

        updateFacultyReq.put("id",facultyIdConst);
        updateFacultyReq.put("name",facultyNameConst);
        updateFacultyReq.put("color",facultyColorConst);


        mockMvc.perform(
                        MockMvcRequestBuilders.put("/faculty/update")
                                .content(updateFacultyReq.toString())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(facultyIdConst))
                .andExpect(jsonPath("$.name").value(facultyNameConst))
                .andExpect(jsonPath("$.color").value(facultyColorConst))
        ;
    }

    @Test
    public void deleteFacultyTest() throws Exception {
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(facultyConst));

        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/faculty/"+facultyIdConst+"/delete")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        ;
    }

    @Test
    public void getFacultyByColorTest() throws Exception {
        when(facultyRepository.findByColor(anyString())).thenReturn(facultyListConst);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/faculty/"+facultyColorConst+"/get-by-color")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(facultyListConst)))
        ;
    }

    @Test
    public void getFacultyByNameOrColorTest() throws Exception {
        when(facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(anyString(), anyString()))
                .thenReturn(facultyListConst);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/faculty/get-by-color-or-name?color="+facultyColorConst+
                                        "&name="+facultyNameConst)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(facultyListConst)))
        ;
    }

    @Test
    public void getAllStudentsOfFacultyTest() throws Exception {

        when(studentRepository.findByFaculty_Id(any(Long.class))).thenReturn(studentListConst);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/faculty/"+facultyIdConst+"/get-students-by-faculty-id")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(studentListConst)))
        ;
    }
}