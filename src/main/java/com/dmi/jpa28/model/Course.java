package com.dmi.jpa28.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString(exclude = {"reviews", "students"})
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries(value = {
        @NamedQuery(name = "query_get_all_courses",
                query = "Select  c  From Course c"),
        @NamedQuery(name = "query_get_100_Step_courses",
                query = "Select  c  From Course c where c.name like '%100 Steps'") })
@Cacheable
@SQLDelete(sql="update course set is_deleted=true where id=?")
@Where(clause="is_deleted = false")
@Slf4j
public class Course {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>();

    @ManyToMany(mappedBy="courses")
    @JsonIgnore
    private List<Student> students = new ArrayList<>();

    @UpdateTimestamp
    private LocalDateTime lastUpdatedDate;

    @CreationTimestamp
    private LocalDateTime createdDate;

    private boolean isDeleted;

    @PreRemove
    private void preRemove(){
        log.info("Setting isDeleted to True");
        this.isDeleted = true;
    }

    public Course(String name) {
        this.name = name;
    }

    public void addReview(Review review) {
        this.reviews.add(review);
        review.setCourse(this);
    }

    public void removeReview(Review review) {
        this.reviews.remove(review);
    }

    public void addStudent(Student student) {
        this.students.add(student);
    }
}
