package com.liquibase.projectliquibase.model.command.update;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class UpdatePetCommand {

    @Pattern(regexp = "[A-Z][a-z]{1,20}", message = "PATTERN_MISMATCH {regexp}")
    private String name;
    @Pattern(regexp = "[A-Z][a-z]{1,25}", message = "PATTERN_MISMATCH {regexp}")
    private String type;

    @NotNull(message = "Birth date is required")
    private LocalDate birthDate;
}
