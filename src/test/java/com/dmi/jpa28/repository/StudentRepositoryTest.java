package com.dmi.jpa28.repository;

import com.dmi.jpa28.DemoApplication;
import com.dmi.jpa28.model.Address;
import com.dmi.jpa28.model.Passport;
import com.dmi.jpa28.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@Slf4j
public class StudentRepositoryTest {

    @Autowired
    StudentRepository repository;

    @Autowired
    EntityManager em;

    //Session & Session Factory


    //EntityManager & Persistence Context
    //Transaction

    @Test
    public void someTest() {
        repository.someOperationToUnderstandPersistenceContext();
    }

    @Test
    @Transactional
    public void setAddressDetails() {
        Student student = em.find(Student.class, 20001L);
        student.setAddress(new Address("No 101", "Some Street", "Hyderabad"));
        em.flush();
    }

    @Test
    @Transactional
    public void retrieveStudentAndPassportDetails() {
        Student student = em.find(Student.class, 20001L);
        log.info("student -> {}", student);
        log.info("passport -> {}",student.getPassport());
    }

    @Test
    @Transactional
    public void retrievePassportAndAssociatedStudent() {
        Passport passport = em.find(Passport.class, 40001L);

        log.info("passport -> {}", passport);
        log.info("student -> {}", passport.getStudent());
    }

    @Test
    @Transactional
    public void retrieveStudentAndCourses() {
        Student student = em.find(Student.class, 20001L);

        log.info("student -> {}", student);
        log.info("courses -> {}", student.getCourses());
    }




}