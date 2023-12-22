package com.example.pocredis.repository;

import com.example.pocredis.model.AnyObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AnyObjectRepository extends JpaRepository<AnyObject, Long> {

    boolean existsByDescription(String description);

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM AnyObject a " +
            "WHERE a.description = :description AND a.id != :id")
    boolean existsByDescriptionNotId(@Param("description") String description, @Param("id") Long id);
}
