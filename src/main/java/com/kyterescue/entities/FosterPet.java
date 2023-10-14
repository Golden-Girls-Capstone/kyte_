package com.kyterescue.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @OneToMany(mappedBy = "fosterPet", cascade = CascadeType.ALL)
    private List<Review> reviews;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    public FosterPet() {

    }
    public FosterPet(LocalDate startDate, LocalDate endDate, User user, Pet pet) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.user = user;
        this.pet = pet;
    }

    public FosterPet(Long id, LocalDate startDate, LocalDate endDate, List<Review> reviews, User user, Pet pet) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reviews = reviews;
        this.user = user;
        this.pet = pet;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) { this.user = user;}
    public Pet getPet() {
        return pet;
    }
    public void setPet(Pet pet){
        this.pet = pet;
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

}
