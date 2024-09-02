package com.sks.sksschool.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "class")
public class SKSClass extends MyBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "class_id_seq")
    @SequenceGenerator(name = "class_id_seq",sequenceName = "class_id_seq",allocationSize = 1)
    private int classId;


    @Size(min = 3 , message = "Class name should be at least 3 chars")
    private String name;

    /*OneToMany as One class(for example class 5, class 6) can have many persons/students*/
    @OneToMany(mappedBy = "sksClass", fetch = FetchType.LAZY,cascade = CascadeType.PERSIST,
            targetEntity = Person.class)
    private Set<Person> persons;
}
