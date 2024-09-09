package com.example.studentservice.controller;

import com.example.studentservice.TestUtils;
import com.example.studentservice.model.Student;
import com.example.studentservice.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class StudentControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private StudentController studentController;

    @Mock
    private StudentService studentService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
    }


    @Test
    public void testAddStudent() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setName("John Doe");
        student.setAge(21);

        when(studentService.addStudent(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders.post("/students")
                        .contentType("application/json")
                        .content(TestUtils.readJsonFile("addStudentRequest.json")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    public void testGetStudent() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setName("John Doe");
        student.setAge(21);

        when(studentService.getStudent(anyLong())).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders.get("/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    public void testGetAllStudents() throws Exception {
        Map<Long, Student> studentsMap = new HashMap<>();
        Student student = new Student();
        student.setId(1L);
        student.setName("John Doe");
        student.setAge(21);
        studentsMap.put(1L, student);

        when(studentService.getAllStudents()).thenReturn(studentsMap);

        mockMvc.perform(MockMvcRequestBuilders.get("/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.1.name").value("John Doe"));
    }

    @Test
    public void testUpdateStudent() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setName("John Doe");
        student.setAge(21);

        when(studentService.updateStudent(anyLong(), any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders.put("/students/1")
                        .contentType("application/json")
                        .content(TestUtils.readJsonFile("updateStudentRequest.json")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    public void testDeleteStudent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/students/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testPartiallyUpdateStudent() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setName("John Doe");
        student.setAge(21);

        when(studentService.partiallyUpdateStudent(anyLong(), any(Map.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders.patch("/students/1")
                        .contentType("application/json")
                        .content(TestUtils.readJsonFile("partialUpdateStudentRequest.json")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    public void testHandleMethodArgumentNotValidException() throws Exception {
        // Setup a situation where MethodArgumentNotValidException would be thrown
        // You can simulate this using mockMvc.perform or a specific test setup

        mockMvc.perform(MockMvcRequestBuilders.post("/students")
                        .contentType("application/json")
                        .content("{\"invalid_field\":\"value\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").value("An unexpected error occurred"));
    }
}
