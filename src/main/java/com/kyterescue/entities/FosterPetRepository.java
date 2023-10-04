package com.kyterescue.entities;

import jakarta.persistence.TypedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FosterPetRepository extends JpaRepository <FosterPet, Long> {
    @Query("FROM FosterPet f WHERE f.user = ?1")
    List<FosterPet> findFosterPetsOfUser(User loggedInUser);
}
