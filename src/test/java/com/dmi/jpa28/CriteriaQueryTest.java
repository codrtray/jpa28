package com.dmi.jpa28;

import com.dmi.jpa28.model.Course;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@Slf4j
public class CriteriaQueryTest {

    @Autowired
    EntityManager em;

    @Test
    public void all_courses() {

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Course> cq = cb.createQuery(Course.class);

        Root<Course> root = cq.from(Course.class);

        TypedQuery<Course> query = em.createQuery(cq.select(root));

        List<Course> resultList = query.getResultList();

        log.info("Typed Query -> {}", resultList);
    }

    @Test
    public void all_courses_having_100Steps() {

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Course> cq = cb.createQuery(Course.class);

        Root<Course> root = cq.from(Course.class);

        Predicate like100 = cb.like(root.get("name"), "%100 Steps");

        cq.where(like100);

        TypedQuery<Course> query = em.createQuery(cq.select(root));

        List<Course> resultList = query.getResultList();

        log.info("Typed Query -> {}", resultList);
    }

    @Test
    public void all_courses_without_students() {

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Course> cq = cb.createQuery(Course.class);

        Root<Course> root = cq.from(Course.class);

        Predicate studentsIsEmpty = cb.isEmpty(root.get("students"));

        cq.where(studentsIsEmpty);

        TypedQuery<Course> query = em.createQuery(cq.select(root));

        List<Course> resultList = query.getResultList();

        log.info("Typed Query -> {}", resultList);
    }

    @Test
    public void join() {

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Course> cq = cb.createQuery(Course.class);

        Root<Course> root = cq.from(Course.class);

        root.join("students");

        TypedQuery<Course> query = em.createQuery(cq.select(root));

        List<Course> resultList = query.getResultList();

        log.info("Typed Query -> {}", resultList);
    }

    @Test
    public void left_join() {

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Course> cq = cb.createQuery(Course.class);

        Root<Course> root = cq.from(Course.class);

        root.join("students", JoinType.LEFT);

        TypedQuery<Course> query = em.createQuery(cq.select(root));

        List<Course> resultList = query.getResultList();

        log.info("Typed Query -> {}", resultList);


    }

}
