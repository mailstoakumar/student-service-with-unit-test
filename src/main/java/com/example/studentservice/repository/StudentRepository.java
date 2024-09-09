package com.example.studentservice.repository;

import com.example.studentservice.model.Student;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class StudentRepository {

    private Map<Long, Student> studentMap = new HashMap<>();
    private long idCounter = 1;

    public Student save(Student student) {
        if (student.getId() == null) {
            student.setId(idCounter++);
        }
        studentMap.put(student.getId(), student);
        return student;
    }

    public Optional<Student> findById(Long id) {
        return Optional.ofNullable(studentMap.get(id));
    }

    public Map<Long, Student> findAll() {
        return studentMap;
    }

    public void deleteById(Long id) {
        studentMap.remove(id);
    }
}

