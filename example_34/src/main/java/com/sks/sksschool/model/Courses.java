package com.sks.sksschool.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;

@Getter
@Setter
@Entity
public class Courses extends MyBaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "courses_id_seq")
    @SequenceGenerator(name = "courses_id_seq",sequenceName = "courses_id_seq",allocationSize = 1)
    private int courseId;

    private String name;

    private String fees;

    @ManyToMany(mappedBy = "courses",cascade =CascadeType.PERSIST,fetch = FetchType.EAGER,
            targetEntity = Person.class)
    private Set<Person> persons;
}
