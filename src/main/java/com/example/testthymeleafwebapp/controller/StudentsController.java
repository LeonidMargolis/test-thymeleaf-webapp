package com.example.testthymeleafwebapp.controller;

import com.example.testthymeleafwebapp.dao.StudentsRepository;
import com.example.testthymeleafwebapp.entity.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Slf4j
@Controller
public class StudentsController {

    @Autowired
    private StudentsRepository studentsRepository;

    @GetMapping({"/list", "/"})
    public ModelAndView getAllStudents() {
        log.info("/list -> connection");
        ModelAndView mav = new ModelAndView("list-students");
        mav.addObject("students",studentsRepository.findAll());
        return mav;
    }

    @GetMapping("/addStudentForm")
    public ModelAndView addStudentsForm(){
        ModelAndView mav = new ModelAndView("add-student-form");
        Student student = new Student();
        mav.addObject("student", student);
        return mav;
    }

    @PostMapping("/saveStudent")
    public String saveStudent(@ModelAttribute Student student) {
        studentsRepository.save(student);
        return "redirect:/list";
    }

    @GetMapping("/showUpdateForm")
    public ModelAndView showUpdateForm(@RequestParam Long studentId){
        ModelAndView mav = new ModelAndView("add-student-form");
        Optional<Student> optionalStudent = studentsRepository.findById(studentId);
        Student student = new Student();
        if (optionalStudent.isPresent()){
            student = optionalStudent.get();
        }
        mav.addObject("student", student);
        return mav;
    }

    @GetMapping("/deleteStudent")
    public String deleteStudent(@RequestParam Long studentId){
        studentsRepository.deleteById(studentId);
        return "redirect:/list";
    }
}
