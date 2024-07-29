package com.liquibase.projectliquibase.model.command.create;


import com.liquibase.projectliquibase.model.Pet;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CreatePetCommand {


    @NotNull(message = "NULL_VALUE")
    @Pattern(regexp = "[A-Z][a-z]{1,20}", message = "PATTERN_MISMATCH {regexp}")
    private String name;

    @NotNull(message = "NULL_VALUE")
    @Pattern(regexp = "[a-z]{1,25}", message = "PATTERN_MISMATCH {regexp}")
    private String type;

    @NotNull
    private LocalDate birthDate;


    public Pet toEntity() {
        return Pet.builder()
                .name(name)
                .type(type)
                .birthDate(birthDate)
                .build();
    }
}
