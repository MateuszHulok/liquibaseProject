package com.liquibase.projectliquibase.repository;

import com.liquibase.projectliquibase.model.Toy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToyRepository extends JpaRepository<Toy, Integer> {
}
