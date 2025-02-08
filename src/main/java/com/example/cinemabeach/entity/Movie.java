package com.example.cinemabeach.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int duration;

    public Movie() {} // Default constructor

    public Movie(String name, int duration) {
        this.name = name;
        this.duration = duration;
    }

    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }
}
