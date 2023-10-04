package com.kyterescue.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "pets")

public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "varchar(100)")
    private String name;

    @Column(columnDefinition = "varchar(100) NOT NULL")
    private String type;

    @Column(columnDefinition = "varchar(100) NOT NULL")
    private String breed;

    @Column(columnDefinition = "varchar(200) NOT NULL")
    private String age;

    @Column(columnDefinition = "varchar(100) NOT NULL")
    private String size;

    @Column(columnDefinition = "varchar(200)")
    private String photo;

    @Column(columnDefinition = "text")
    private String description;

    @Column(columnDefinition = "varchar(100) NOT NULL")
    private String gender;

    @Column(columnDefinition = "tinyint NOT NULL")
    private int status;

    @OneToMany(mappedBy = "pet")
    private List<FosterPet> fosterPets;

    @ManyToMany(mappedBy = "pets")
    private List<User> users;

    public Pet(){

    }
    public Pet(String name, String type, String breed, String age, String size, String photo, String description, String gender, int status) {
        this.name = name;
        this.type = type;
        this.breed = breed;
        this.age = age;
        this.size = size;
        this.photo = photo;
        this.description = description;
        this.gender = gender;
        this.status = status;
    }

    public Long getId() {return id;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
