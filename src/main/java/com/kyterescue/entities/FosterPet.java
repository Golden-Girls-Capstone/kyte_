package com.kyterescue.entities;


import jakarta.persistence.*;
import org.springframework.security.core.parameters.P;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "foster_pets")

public class FosterPet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "DATE NOT NULL")
    private Date start_date;

    @Column(columnDefinition = "DATE NOT NULL")
    private Date end_date;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    public FosterPet(){

    }

    public FosterPet(Long id, Date start_date, Date end_date) {
        this.id = id;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public FosterPet(Date start_date, Date end_date) {
        this.start_date = start_date;
        this.end_date = end_date;
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

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

}
