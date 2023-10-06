package com.kyterescue.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    @Query("FROM Pet p WHERE p.id = ?1")
    Pet getPetById(long id);

}
