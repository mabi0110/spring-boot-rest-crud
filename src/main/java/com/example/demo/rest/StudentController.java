package com.example.demo.rest;


import com.example.demo.entity.Student;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentController {

    private final List<Student> studentsList = new ArrayList<>();

    @PostConstruct
    public void init(){
        studentsList.add(new Student("Ala", "Nowak"));
        studentsList.add(new Student("Ola", "Nowak"));
        studentsList.add(new Student("Adam", "Nowak"));
    }

    @GetMapping("/students")
    public List<Student> getStudents() {
         return studentsList;

    }

    @GetMapping("students/{studentId}")
    public Student getStudent(@PathVariable int studentId) {
        if (studentId < 0 || studentId >= studentsList.size()) {
            throw new StudentNotFoundException("Student with id: " + studentId + " not found");
        }
        return studentsList.get(studentId);
    }
}
