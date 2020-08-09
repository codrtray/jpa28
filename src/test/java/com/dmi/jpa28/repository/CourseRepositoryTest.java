package com.dmi.jpa28.repository;

import com.dmi.jpa28.DemoApplication;
import com.dmi.jpa28.model.Course;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@Slf4j
public class CourseRepositoryTest {

    @Autowired
    CourseRepository repository;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void findById_basic() {
        Course course = repository.findById(10001L);
        assertEquals("JPA in 50 Steps", course.getName());
    }

    @Test
    @Transactional
    public void findById_firstLevelCacheDemo() {
        Course course = repository.findById(10001L);
        log.info("First Course Retrieved {}", course);

        Course course1 = repository.findById(10001L);
        log.info("First Course Retrieved again {}", course1);

        assertEquals("JPA in 50 Steps", course.getName());

        assertEquals("JPA in 50 Steps", course1.getName());

    }

    @Test
    @DirtiesContext
    public void deleteById_basic() {
        repository.deleteById(10002L);
        assertNull(repository.findById(10002L));
    }

    @Test
    @DirtiesContext
    public void save_basic() {

        // get a course
        Course course = repository.findById(10001L);
        assertEquals("JPA in 50 Steps", course.getName());

        // update details
        course.setName("JPA in 50 Steps - Updated");

        repository.save(course);

        // check the value
        Course course1 = repository.findById(10001L);
        assertEquals("JPA in 50 Steps - Updated", course1.getName());
    }

    @Test(expected = PersistenceException.class)
    @Transactional
    public void saveFail() {

        Course course1 = new Course();
        em.persist(course1);
        em.flush();

    }

}