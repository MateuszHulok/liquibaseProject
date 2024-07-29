package com.liquibase.projectliquibase.model.dto;


import com.liquibase.projectliquibase.model.Toy;
import lombok.Builder;
import lombok.Getter;
@Getter
@Builder
public class ToyDto {

    private int id;
    private String name;
    private double price;
    private int petId;

    public static ToyDto formEntity(Toy toy) {
        return ToyDto.builder()
                .id(toy.getId())
                .name(toy.getName())
                .price(toy.getPrice())
                .petId(toy.getPet().getId())
                .build();
    }
}
