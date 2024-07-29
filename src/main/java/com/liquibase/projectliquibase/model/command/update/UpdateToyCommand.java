package com.liquibase.projectliquibase.model.command.update;

import lombok.Data;

@Data
public class UpdateToyCommand {

    private String name;
    private double price;
}
