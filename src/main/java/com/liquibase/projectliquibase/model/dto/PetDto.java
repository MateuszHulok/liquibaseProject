package com.liquibase.projectliquibase.model.dto;


import com.liquibase.projectliquibase.model.Pet;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class PetDto {

    private int id;
    private String name;
    private String type;
    private LocalDate birthDate;

    public static PetDto fromEntity(Pet pet) {
        return PetDto.builder()
                .id(pet.getId())
                .name(pet.getName())
                .type(pet.getType())
                .birthDate(pet.getBirthDate())
                .build();
    }
}
