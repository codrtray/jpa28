package com.dmi.jpa28;

import com.dmi.jpa28.model.Course;
import com.dmi.jpa28.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@Slf4j
public class JPQLTest {

    @Autowired
    private EntityManager em;

    @Test
    public void jpql_basic() {
        Query query = em.createNamedQuery("query_get_all_courses");
        log.info("Select  c  From Course c -> {}", query.getResultList());
    }

    @Test
    public void jpql_typed() {
        TypedQuery<Course> query = em.createQuery("select c from Course c", Course.class);
        log.info("Select  c  From Course c -> {}", query.getResultList());
    }

    @Test
    public void jpql_where() {

        TypedQuery<Course> query =
                em.createNamedQuery("query_get_100_Step_courses", Course.class);

        log.info("Select  c  From Course c where name like '%100 Steps'-> {}", query.getResultList());

    }

    @Test
    public void jpql_courses_without_students() {
        TypedQuery<Course> query = em.createQuery("Select c from Course c where c.students is empty", Course.class);
        List<Course> resultList = query.getResultList();
        log.info("Results -> {}", resultList);
        // [Course[Spring in 50 Steps]]
    }

    @Test
    public void jpql_courses_with_atleast_2_students() {
        TypedQuery<Course> query = em.createQuery("Select c from Course c where size(c.students) >= 2", Course.class);
        List<Course> resultList = query.getResultList();
        log.info("Results -> {}", resultList);
        //[Course[JPA in 50 Steps]]
    }

    @Test
    public void jpql_courses_ordered_by_students() {
        TypedQuery<Course> query = em.createQuery("Select c from Course c order by size(c.students) desc", Course.class);
        List<Course> resultList = query.getResultList();
        log.info("Results -> {}", resultList);
    }

    @Test
    public void jpql_students_with_passports_in_a_certain_pattern() {
        TypedQuery<Student> query = em.createQuery("Select s from Student s where s.passport.number like '%1234%'", Student.class);
        List<Student> resultList = query.getResultList();
        log.info("Results -> {}", resultList);
    }

    @Test
    public void join(){
        Query query = em.createQuery("Select c, s from Course c JOIN c.students s");
        List<Object[]> resultList = query.getResultList();
        log.info("Results Size -> {}", resultList.size());
        for(Object[] result:resultList){
            log.info("Course{} Student{}", result[0], result[1]);
        }
    }

    @Test
    public void left_join(){
        Query query = em.createQuery("Select c, s from Course c LEFT JOIN c.students s");
        List<Object[]> resultList = query.getResultList();
        log.info("Results Size -> {}", resultList.size());
        for(Object[] result:resultList){
            log.info("Course{} Student{}", result[0], result[1]);
        }
    }

    @Test
    public void cross_join(){
        Query query = em.createQuery("Select c, s from Course c, Student s");
        List<Object[]> resultList = query.getResultList();
        log.info("Results Size -> {}", resultList.size());
        for(Object[] result:resultList){
            log.info("Course{} Student{}", result[0], result[1]);
        }
    }


}
