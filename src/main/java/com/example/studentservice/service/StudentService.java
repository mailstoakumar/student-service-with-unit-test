package com.example.studentservice.service;

import com.example.studentservice.exception.StudentNotFoundException;
import com.example.studentservice.model.Student;
import com.example.studentservice.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student getStudent(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    public Map<Long, Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student updateStudent(Long id, Student student) {
        if (!studentRepository.findById(id).isPresent()) {
            throw new StudentNotFoundException(id);
        }
        student.setId(id);
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        if (!studentRepository.findById(id).isPresent()) {
            throw new StudentNotFoundException(id);
        }
        studentRepository.deleteById(id);
    }

    // Partially update a student by ID
    public Student partiallyUpdateStudent(Long id, Map<String, Object> updates) {
        Student student = getStudent(id);

        updates.forEach((key, value) -> {
            switch (key) {
                case "name":
                    student.setName((String) value);
                    break;
                case "age":
                    student.setAge((Integer) value);
                    break;
                default:
                    throw new RuntimeException("Invalid field: " + key);
            }
        });

        return studentRepository.save(student);
    }
}
