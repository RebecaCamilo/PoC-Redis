package com.example.pocredis.repository;

import com.example.pocredis.model.AnyObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnyObjectRepository extends JpaRepository<AnyObject, Long> {

    boolean existsByDescription(String description);

    boolean existsByDescriptionNotId(String description, Long id);
}
