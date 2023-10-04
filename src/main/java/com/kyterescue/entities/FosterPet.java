package com.kyterescue.entities;


import jakarta.persistence.*;
import org.springframework.security.core.parameters.P;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "foster_pets")

public class FosterPet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "DATE NOT NULL")
    private LocalDate startDate;
    @Column(columnDefinition = "DATE NOT NULL")
    private LocalDate endDate;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;
    @Column(columnDefinition = "VARCHAR(250)")
    private String fosterReviews;

    public FosterPet() {

    }
    public FosterPet(LocalDate startDate, LocalDate endDate, User user, Pet pet) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.user = user;
        this.pet = pet;
    }
    public FosterPet(Long id, LocalDate startDate, LocalDate endDate, String fosterReviews, User user, Pet pet) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.fosterReviews = fosterReviews;
        this.user = user;
        this.pet = pet;
    }
    public User getUser() {
        return user;
    }
    public Pet getPet() {
        return pet;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public ChronoLocalDate getStart_date() {
        return startDate;
    }
    public void setStart_date(LocalDate start_date) {
        this.startDate = start_date;
    }
    public ChronoLocalDate getEnd_date() {
        return endDate;
    }
    public void setEnd_date(LocalDate end_date) {
        this.endDate = end_date;
    }
    public String getFoster_reviews() {
        return fosterReviews;
    }
    public void setFoster_reviews(String foster_reviews) {
        this.fosterReviews = foster_reviews;
    }


}
