package com.kyterescue.entities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FosterPetRepository extends JpaRepository <FosterPet, Long> {

    @Query("FROM FosterPet f WHERE f.user = ?1")
    List<FosterPet> findFosterPetsOfUser(User loggedInUser);
}
