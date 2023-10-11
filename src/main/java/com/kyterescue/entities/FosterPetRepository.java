package com.kyterescue.entities;

import jakarta.persistence.TypedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface FosterPetRepository extends JpaRepository <FosterPet, Long> {

    @Query("FROM FosterPet f WHERE f.id = ?1")
    FosterPet findFosterPetById(long id);
    @Query("FROM FosterPet f WHERE f.pet = ?1")
    FosterPet findFosterPetByPetId(Pet pet);
    @Query("FROM FosterPet f WHERE f.user = ?1")
    List<FosterPet> findFosterPetsOfUser(User loggedInUser);

    FosterPet findByPetAndUserAndStartDateAndEndDate(Pet pet, User user, LocalDate startDate, LocalDate endDate);
}
