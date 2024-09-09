package com.example.studentservice.repository;

import com.example.studentservice.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class StudentRepositoryTest {

    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        studentRepository = new StudentRepository();
    }

    @Test
    void testSaveAndFindById() {
        Student student = new Student();
        student.setName("John Doe");
        student.setAge(21);

        Student savedStudent = studentRepository.save(student);
        assertNotNull(savedStudent.getId());

        Optional<Student> foundStudent = studentRepository.findById(savedStudent.getId());
        assertTrue(foundStudent.isPresent());
        assertEquals("John Doe", foundStudent.get().getName());
    }

    @Test
    void testFindAll() {
        Student student1 = new Student();
        student1.setName("John Doe");
        student1.setAge(21);
        studentRepository.save(student1);

        Student student2 = new Student();
        student2.setName("Jane Doe");
        student2.setAge(22);
        studentRepository.save(student2);

        Map<Long, Student> allStudents = studentRepository.findAll();
        assertEquals(2, allStudents.size());
    }

    @Test
    void testDeleteById() {
        Student student = new Student();
        student.setName("John Doe");
        student.setAge(21);

        Student savedStudent = studentRepository.save(student);
        studentRepository.deleteById(savedStudent.getId());

        Optional<Student> foundStudent = studentRepository.findById(savedStudent.getId());
        assertFalse(foundStudent.isPresent());
    }
}
