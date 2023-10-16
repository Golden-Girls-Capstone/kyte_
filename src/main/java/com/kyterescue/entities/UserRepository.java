package com.kyterescue.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {
    @Query("FROM User u WHERE u.id LIKE ?1")
    User getUserById(long id);
    User findByUsername(String username);
    User findByEmail(String email);
}
