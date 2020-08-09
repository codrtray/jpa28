package com.dmi.jpa28.repository;

import com.dmi.jpa28.DemoApplication;
import com.dmi.jpa28.model.Course;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@Slf4j
public class CourseSpringDataRepositoryTest {

    @Autowired
    CourseSpringDataRepository repository;


    @Test
    public void findaById_CoursePresent() {

        Optional<Course> byId = repository.findById(10001L);
        assertTrue(byId.isPresent());
    }

    @Test
    public void playingAroundWithSpringDataRepository() {
        log.info("Courses -> {}", repository.findAll());
        log.info("Courses -> {}", repository.count());
    }

    @Test
    public void sort() {

        Sort sort = new Sort(Sort.Direction.ASC, "name");
        log.info("Sorted Courses -> {} ", repository.findAll(sort));
    }

    @Test
    public void pagination() {

        PageRequest pageRequest = PageRequest.of(0, 2);
        Page<Course> coursePage = repository.findAll(pageRequest);
        log.info("First page -> {} ", coursePage.getContent());

        Pageable pageable = coursePage.nextPageable();
        Page<Course> second = repository.findAll(pageable);
        log.info("Second Page -> {} ", second.getContent());
    }
}