package com.dmi.jpa28;

import com.dmi.jpa28.model.FullTimeEmployee;
import com.dmi.jpa28.model.PartTimeEmployee;
import com.dmi.jpa28.repository.CourseRepository;
import com.dmi.jpa28.repository.EmployeeRepository;
import com.dmi.jpa28.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.math.BigDecimal;

//@Component
@Slf4j
public class StartSetUp implements CommandLineRunner {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public void run(String... args) throws Exception {
//        Course course = courseRepository.findById(10001L);
//        log.info("Course 10001 -> {}", course);
//        courseRepository.deleteById(10001L);
//        courseRepository.save(new Course("Microservices in 100 Steps"));
//        courseRepository.save(new Course(10003L, "Microservices in 100 Steps"));

//        courseRepository.playWithEntityManager1();
//        courseRepository.playWithEntityManager2();
//        courseRepository.playWithEntityManager3();
//        courseRepository.playWithEntityManager4();

//        courseRepository.addReviewsForCourse();


//        List<Review> reviews = new ArrayList<>();
//
//        reviews.add(new Review("5", "Great Hands-on Stuff."));
//        reviews.add(new Review("5", "Hatsoff."));
//
//        courseRepository.addReviewsForCourseLoop(10003L, reviews );

//        studentRepository.insertHardcodedStudentAndCourse();

        employeeRepository.insert(new PartTimeEmployee("Jill", new BigDecimal("50")));
        employeeRepository.insert(new FullTimeEmployee("Jack", new BigDecimal("10000")));

        log.info("All Employees -> {}", employeeRepository.retrieveAllEmployees());


    }
}
