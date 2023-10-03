package com.kyterescue.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min = 4, max = 30, message = "Username must be between 4 and 30 characters!")
    @Column(unique = true)
    private String username;

    @Size(min = 4, max = 50, message = "Email must be between 4 and 50 characters!")
    @Column(unique = true)
    private String email;

    @Size(min = 4, max = 200, message = "Password must be between 4 and 16 characters!")
    @Column
    private String password;

    @Size(max = 500)
    @Column
    private String picture;

    @Column(nullable = false)
    private boolean approvalStatus;

    @Column(columnDefinition = "INT(11) UNSIGNED")
    private int zipcode;

    @OneToMany(mappedBy = "user")
    private List<FosterPet> fosterPets;

    @ManyToMany(mappedBy = "users")
    private List<Badge> badges;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "user_favorite_pets",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "pet_id")
    )
    private List<Pet> pets;

    public User() {

    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(String username, String email, String password, String picture, boolean approvalStatus, int zipcode) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.picture = picture;
        this.approvalStatus = approvalStatus;
        this.zipcode = zipcode;
    }

    public User(String username, String email, String password, String picture, boolean approvalStatus) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.picture = picture;
        this.approvalStatus = approvalStatus;
    }

    public User(String username, String email, String password, boolean approvalStatus, int zipcode) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.approvalStatus = approvalStatus;
        this.zipcode = zipcode;
    }

    public User(String username, String email, String password, boolean approvalStatus) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.approvalStatus = approvalStatus;
    }

    public User(long id, String username, String email, String password, String picture, boolean approvalStatus, int zipcode) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.picture = picture;
        this.approvalStatus = approvalStatus;
        this.zipcode = zipcode;
    }

    public User(User copy) {
        id = copy.id;
        email = copy.email;
        username = copy.username;
        password = copy.password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public boolean isApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(boolean approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }
}
