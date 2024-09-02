package com.sks.sksschool.controller;

import com.sks.sksschool.model.Courses;
import com.sks.sksschool.model.Person;
import com.sks.sksschool.model.SKSClass;
import com.sks.sksschool.repository.CoursesRepository;
import com.sks.sksschool.repository.PersonRepository;
import com.sks.sksschool.repository.SKSClassRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    SKSClassRepository sksClassRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    CoursesRepository coursesRepository;
    
    @GetMapping("/displayClasses")
    public ModelAndView displayClasses(){
        ModelAndView modelAndView = new ModelAndView("classes.html");
        List<SKSClass> sksClasses = sksClassRepository.findAll();
        modelAndView.addObject("sksClasses",sksClasses);
        modelAndView.addObject("sksClass",new SKSClass());
        return modelAndView;
    }

    @PostMapping("/addClass")
    public String addClass(@Valid @ModelAttribute("sksClass") SKSClass sksClass, Errors errors){
        
        if(errors.hasErrors()){
            return "displayMessages.html";
        }
        sksClassRepository.save(sksClass);
        return "redirect:/admin/displayClasses";
    }

    @GetMapping("/deleteClass")
    public String deleteClass(@RequestParam(value = "classId") Integer classId){

        /*We are making the sksClass as null for the Persons who were registered to that class,
        * as we are deleting the class*/
        Optional<SKSClass> sksClass=sksClassRepository.findById(classId);
        for(Person person:sksClass.get().getPersons()){
            person.setSksClass(null);
            personRepository.save(person);
        }
        sksClassRepository.deleteById(classId);
        return "redirect:/admin/displayClasses";
    }

    @GetMapping("/displayStudents")
    public ModelAndView displayStudents(@RequestParam(value = "classId") Integer classId,
                                        HttpSession httpSession,
                                        @RequestParam(value = "error",required = false) String error){

       ModelAndView modelAndView = new ModelAndView("student.html");
        Optional<SKSClass> sksClass = sksClassRepository.findById(classId);
        modelAndView.addObject("sksClass",sksClass.get());
        modelAndView.addObject("person",new Person());

        httpSession.setAttribute("sksClass",sksClass.get());

        if(error !=null){
            modelAndView.addObject("errorMessage",error);
        }
        return modelAndView;
    }

    @PostMapping("/addNewStudent")
    public String addNewStudent(@ModelAttribute(value = "person") Person person,
                                HttpSession httpSession ){

        SKSClass sksClass= (SKSClass) httpSession.getAttribute("sksClass");

        Person fetchedPerson = personRepository.findByEmail(person.getEmail());

        if(null == fetchedPerson){
            return "redirect:/admin/displayStudents?classId="+sksClass.getClassId()+"&error=invalid email entered";
        }

        fetchedPerson.setSksClass(sksClass);
        personRepository.save(fetchedPerson);
        sksClass.getPersons().add(fetchedPerson);
        SKSClass savedClass=sksClassRepository.save(sksClass);
        httpSession.setAttribute("sksClass",savedClass);

        return "redirect:/admin/displayStudents?classId="+sksClass.getClassId();

    }

    @GetMapping("deleteStudent")
    public String deleteStudent(@RequestParam(value = "email")String email, HttpSession httpSession){

       Person person = personRepository.findByEmail(email);
       person.setSksClass(null);
       personRepository.save(person);

        SKSClass sksClass = (SKSClass) httpSession.getAttribute("sksClass");
        sksClass.getPersons().remove(person);
        SKSClass savedClass=sksClassRepository.save(sksClass);
        httpSession.setAttribute("sksClass",savedClass);

        return "redirect:/admin/displayStudents?classId="+sksClass.getClassId();

    }

    @GetMapping("/displayCourses")
    public ModelAndView displayCourses(){
        ModelAndView modelAndView = new ModelAndView("courses_admin.html");
        List<Courses> courses = coursesRepository.findAll();
        modelAndView.addObject("courses",courses);
        modelAndView.addObject("course",new Courses());
        return modelAndView;
    }

    @PostMapping("/addNewCourse")
    public String addNewCourse(@ModelAttribute(value = "course") Courses courses){
        coursesRepository.save(courses);
        return "redirect:/admin/displayCourses";
    }

    @GetMapping("/viewStudents_Course")
    public ModelAndView viewStudentsForCourses(@RequestParam(value = "courseId") Integer courseId,HttpSession httpSession,
                                               @RequestParam(value = "error", required = false) String error){
        Optional<Courses> courses = coursesRepository.findById(courseId);
        ModelAndView modelAndView = new ModelAndView("students_course");
        modelAndView.addObject("courses",courses.get());
        modelAndView.addObject("person",new Person());

        httpSession.setAttribute("course",courses.get());

        if(error !=null){
            modelAndView.addObject("errorMessage",error);
        }
        return modelAndView;

    }

    @PostMapping("/addStudent_Course")
    public String addStudentsForCourse(@ModelAttribute Person person,HttpSession httpSession){
        Person fetchedPerson = personRepository.findByEmail(person.getEmail());
        Courses courses = (Courses) httpSession.getAttribute("course");
        if(fetchedPerson == null){
            return "redirect:/admin/viewStudents_Course?courseId="+courses.getCourseId()
                    +"error=invalid emailId entered";
        }
        fetchedPerson.getCourses().add(courses);
        personRepository.save(fetchedPerson);
        courses.getPersons().add(fetchedPerson);
        Courses savedCourses = coursesRepository.save(courses);
        httpSession.setAttribute("course",savedCourses);
        return "redirect:/admin/viewStudents_Course?courseId="+savedCourses.getCourseId();
    }

    @GetMapping("/deleteStudent_Course")
    public String deleteStudentForCourse(@RequestParam String email,HttpSession httpSession){

        Person person = personRepository.findByEmail(email);
        person.setCourses(null);
        personRepository.save(person);

        Courses courses = (Courses) httpSession.getAttribute("course");
        courses.getPersons().remove(person);
        Courses savedCourse = coursesRepository.save(courses);

        httpSession.setAttribute("course",savedCourse);
        return "redirect:/admin/viewStudents_Course?courseId="+savedCourse.getCourseId();
    }
}
