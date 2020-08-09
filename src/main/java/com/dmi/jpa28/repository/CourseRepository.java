package com.dmi.jpa28.repository;

import com.dmi.jpa28.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
@Transactional
@Slf4j
public class CourseRepository {

    @PersistenceContext
    @NotNull
    private EntityManager em;

    public Course findById(Long id) {
        return em.find(Course.class, id);
    }

    public void deleteById(Long id){
        Course course = findById(id);
        em.remove(course);
    }

    public Course save(Course course) {
        if (course.getId() == null) {
            em.persist(course);
        } else {
            em.merge(course);
        }
        return course;
    }

    public void playWithEntityManager1() {
        log.info("Point 1");
        Course course = new Course("Web Services in 100 Steps");
        log.info("Point 2");
        em.persist(course);
        log.info("Point 3");
        em.flush();
        log.info("Point 4");
        course.setName("Web Services in 100 Steps - Updated");
        log.info("Point 5");
    }

    public void playWithEntityManager2() {
        Course course1 = new Course("Web Services in 100 Steps");
        em.persist(course1);
        em.flush();

        Course course2 = new Course("Angular js in 100 steps");
        em.persist(course2);
        em.flush();

        em.clear();

        course1.setName("Web Services in 100 Steps - updated");
        em.flush();

        em.detach(course2);

        course2.setName("Angular js in 100 steps - updated");
        em.flush();
    }

    public void playWithEntityManager3() {
        Course course1 = new Course("Web Services in 100 Steps");
        em.persist(course1);
        Course course2 = new Course("Angular js in 100 steps");
        em.persist(course2);
        em.flush();

        course1.setName("Web Services in 100 Steps - updated");
        course2.setName("Angular js in 100 steps - updated");
        em.refresh(course2);
    }

    public void playWithEntityManager4() {

        log.info("Point 1");
        Student student = em.find(Student.class, 20001L);
        log.info("Point 2");
        Passport passport = student.getPassport();
        log.info("Point 3 {}", passport.getNumber());
    }

    public void addReviewsForCourse() {
        //get the course 10003
        Course course = findById(10003L);
        log.info("course.getReviews() -> {}", course.getReviews());

        //add 2 reviews to it
        Review review1 = new Review(ReviewRating.FIVE, "Great Hands-on Stuff.");
        Review review2 = new Review(ReviewRating.FIVE, "Hatsoff.");

        //setting the relationship
        course.addReview(review1);
//        review1.setCourse(course);

        course.addReview(review2);
//        review2.setCourse(course);

        //save it to the database
        em.persist(review1);
        em.persist(review2);
    }

    public void addReviewsForCourseLoop(Long courseId, List<Review> reviews) {

        Course course = findById(courseId);
        log.info("course.getReviews() -> {}", course.getReviews());

        for (Review review : reviews) {
            course.addReview(review);
            em.persist(review);

        }
        em.flush();

    }
}
