package com.example.demo.rest;


import com.example.demo.entity.Student;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException(StudentNotFoundException ex){
        StudentErrorResponse response = new StudentErrorResponse();
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setMessage(ex.getMessage());
        response.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
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
