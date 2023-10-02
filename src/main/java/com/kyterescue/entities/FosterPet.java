package com.kyterescue.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "foster_pets")

public class FosterPet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "DATE NOT NULL")
    private String start_date;

    @Column(columnDefinition = "DATE NOT NULL")
    private String end_date;

    public FosterPet(){

    }

    public FosterPet(Long id, String start_date, String end_date) {
        this.id = id;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public FosterPet(String start_date, String end_date) {
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }
}
