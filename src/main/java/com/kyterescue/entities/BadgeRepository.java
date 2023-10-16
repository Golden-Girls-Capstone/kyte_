package com.kyterescue.entities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BadgeRepository extends JpaRepository <Badge, Long> {

    Badge findBadgeById(long id);
}
