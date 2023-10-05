package com.kyterescue.entities;

import jakarta.persistence.TypedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FosterPetRepository extends JpaRepository <FosterPet, Long> {

    @Query("FROM FosterPet f WHERE f.id = ?1")
    FosterPet findFosterPetById(long id);
    @Query("FROM FosterPet f WHERE f.pet = ?1")
    FosterPet findFosterPetByPetId(Pet pet);
    @Query("FROM FosterPet f WHERE f.user = ?1")
    List<FosterPet> findFosterPetsOfUser(User loggedInUser);
    @Query(value = "SELECT foster_reviews FROM foster_pets f WHERE f.pet_id = ?1", nativeQuery = true)
    List<String> findReviewsOfFoster(long petId);
}
