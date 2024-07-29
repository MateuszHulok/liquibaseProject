package com.liquibase.projectliquibase.model.command.create;

import com.liquibase.projectliquibase.model.Toy;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateToyCommand {

    @NotNull(message = "NULL_VALUE")
    private String name;

    @Positive(message = "ILLEGAL_VALUE")
    private double price;

    public Toy toEntity() {
        return Toy.builder()
                .name(name)
                .price(price)
                .build();
    }
}
