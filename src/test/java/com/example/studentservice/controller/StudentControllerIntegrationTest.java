package com.example.studentservice.controller;


import com.example.studentservice.TestUtils;
import com.example.studentservice.model.Student;
import com.example.studentservice.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")  // Use a test profile if you have one
public class StudentControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StudentRepository studentRepository;

    @BeforeEach
    public void setUp() {
        // Clean up the repository before each test

    }

    @Test
    public void testAddStudent() {
        Student student = new Student();
        student.setName("John Doe");
        student.setAge(21);


        ResponseEntity<Student> response = restTemplate.postForEntity("/students", student, Student.class);

        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("John Doe");
    }

    @Test
    public void testGetStudent() {
        Student student = new Student();
        student.setName("John Doe");
        student.setAge(21);
        student = studentRepository.save(student);

        ResponseEntity<Student> response = restTemplate.getForEntity("/students/" + student.getId(), Student.class);

        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("John Doe");
    }

    @Test
    public void testGetAllStudents() {
        Student student1 = new Student();
        student1.setName("John Doe");
        student1.setAge(21);

        studentRepository.save(student1);

        Student student2 = new Student();
        student2.setName("Jane Smith");
        student2.setAge(22);

        studentRepository.save(student2);

        ResponseEntity<Map> response = restTemplate.getForEntity("/students", Map.class);

        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(((Map) response.getBody()).size()).isGreaterThan(1);
    }

    @Test
    public void testUpdateStudent() {
        Student student = new Student();
        student.setName("John Doe");
        student.setAge(21);

        student = studentRepository.save(student);

        student.setName("John Updated");
        HttpEntity<Student> requestEntity = new HttpEntity<>(student);
        ResponseEntity<Student> response = restTemplate.exchange("/students/" + student.getId(), HttpMethod.PUT, requestEntity, Student.class);

        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("John Updated");
    }

    @Test
    public void testDeleteStudent() {
        Student student = new Student();
        student.setName("John Doe");
        student.setAge(21);

        student = studentRepository.save(student);

        restTemplate.delete("/students/" + student.getId());

        ResponseEntity<Student> response = restTemplate.getForEntity("/students/" + student.getId(), Student.class);

        assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);
    }


    @Test
    public void testHandleMethodArgumentNotValidException() throws IOException {
        // Read JSON from file
        String invalidStudentJson = TestUtils.getStringFormattedJsonFile("addStudentRequest_invalid.json");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(invalidStudentJson, headers);

        ResponseEntity<String> response = restTemplate.exchange("/students", HttpMethod.POST, requestEntity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
        assertThat(response.getBody()).contains("An unexpected error occurred");
    }
}
