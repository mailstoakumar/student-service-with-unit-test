package com.example.studentservice.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Student {

    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 10, message = "Name must be between 2 and 10 characters")
    private String name;

    @NotNull(message = "Age is required")
    @Min(value = 18, message = "Age must be 18 or older")
    private int age;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
