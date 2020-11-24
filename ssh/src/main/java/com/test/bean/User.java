package com.test.bean;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "students")
public class User {
    @Column(name = "name")
    private String name;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "city")
    private String city;
    @Column(name = "age")
    private int age;
}
