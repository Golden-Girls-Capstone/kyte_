package com.kyterescue.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "badges")

public class Badge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "varchar(100) NOT NULL")
    private String name;

    @Column(columnDefinition = "varchar(255)")
    private String img;

    public Badge(Long id, String name, String img) {
        this.id = id;
        this.name = name;
        this.img = img;
    }

    public Badge(Long id, String img) {
        this.id = id;
        this.img = img;
    }

    public Badge(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
