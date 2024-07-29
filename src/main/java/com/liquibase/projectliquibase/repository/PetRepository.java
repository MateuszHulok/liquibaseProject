package com.liquibase.projectliquibase.repository;

import com.liquibase.projectliquibase.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Integer> {
}
