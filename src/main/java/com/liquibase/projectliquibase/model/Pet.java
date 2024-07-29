package com.liquibase.projectliquibase.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDate;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@SQLDelete(sql = "UPDATE pet SET deleted = '1' WHERE id = ?")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String type;
    private LocalDate birthDate;
    @OneToMany(mappedBy = "pet")
    private Set<Toy> toys;
    private boolean deleted;
}
