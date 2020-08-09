package com.dmi.jpa28.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "course")
@Cacheable
public class Review {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private ReviewRating rating;


    private String description;

    @ManyToOne
    private Course course;

    public Review(ReviewRating rating, String description) {
        this.rating = rating;
        this.description = description;
    }
}
