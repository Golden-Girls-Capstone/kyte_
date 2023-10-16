package com.kyterescue.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
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
    @Column(columnDefinition = "BIGINT(11) UNSIGNED")
    private Long zipcode = null;
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<FosterPet> fosterPets;
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Review> reviews;
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_badges",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "badge_id")
    )
    private List<Badge> badges = new ArrayList<>();
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_favorite_pets",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "pet_id")
    )
    private List<Pet> favorites = new ArrayList<>();

    public User() {

    }
    public User(String username, String email, String password, Long zipcode) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.zipcode = zipcode;
    }
    public User(
            String username,
            String email,
            String password,
            String picture,
            boolean approvalStatus,
            Long zipcode
    ){
        this.username = username;
        this.email = email;
        this.password = password;
        this.picture = picture;
        this.approvalStatus = approvalStatus;
        this.zipcode = zipcode;
    }
    public User(
            String username,
            String email,
            String password,
            String picture,
            boolean approvalStatus
    ){
        this.username = username;
        this.email = email;
        this.password = password;
        this.picture = picture;
        this.approvalStatus = approvalStatus;
    }

    public User(
            String username,
            String email,
            String password,
            boolean approvalStatus,
            Long zipcode
    ){
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

    public User(
            long id,
            String username,
            String email,
            String password,
            String picture,
            boolean approvalStatus,
            Long zipcode
    ){
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
    public Long getZipcode() {
        return zipcode;
    }
    public void setZipcode(Long zipcode) {
        this.zipcode = zipcode;
    }
    @JsonIgnore
    public List<FosterPet> getFosterPets() {
        return fosterPets;
    }
    public void setFosterPets(List<FosterPet> fosterPets) {
        this.fosterPets = fosterPets;
    }
    @JsonIgnore
    public List<Badge> getBadges() {
        return badges;
    }
    public void setBadges(List<Badge> badges) {
        this.badges = badges;
    }
    @JsonIgnore
    public List<Pet> getFavorites() {
        return favorites;
    }
    public void setFavorites(List<Pet> favorites) {
        this.favorites = favorites;
    }
    @JsonIgnore
    public List<Review> getReviews() {
        return reviews;
    }
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
