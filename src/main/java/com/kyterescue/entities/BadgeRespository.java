package com.kyterescue.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BadgeRespository extends JpaRepository <Badge, Long> {

    @Query("FROM Badge b WHERE b.id LIKE ?1")
    Badge getBadgeById(long id);
}
