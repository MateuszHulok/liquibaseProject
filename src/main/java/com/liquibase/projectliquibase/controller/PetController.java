package com.liquibase.projectliquibase.controller;


import com.liquibase.projectliquibase.model.command.create.CreatePetCommand;
import com.liquibase.projectliquibase.model.command.create.CreateToyCommand;
import com.liquibase.projectliquibase.model.command.update.UpdatePetCommand;
import com.liquibase.projectliquibase.model.command.update.UpdateToyCommand;
import com.liquibase.projectliquibase.model.dto.PetDto;
import com.liquibase.projectliquibase.model.dto.ToyDto;
import com.liquibase.projectliquibase.service.PetService;
import com.liquibase.projectliquibase.service.ToyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/pets")
@RequiredArgsConstructor
public class PetController {


    private final PetService petService;
    private final ToyService toyService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PetDto save(@RequestBody @Valid CreatePetCommand command) {
        return petService.save(command);
    }


    @GetMapping
    public List<PetDto> findAll() {
        return petService.findAll();
    }

    @GetMapping("/{id}")
    public PetDto findById(@PathVariable int id) {
        return petService.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public PetDto update(@PathVariable int id, @RequestBody @Valid UpdatePetCommand command) {
        return petService.update(id, command);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePet(@PathVariable int id) {
        petService.deleteById(id);
    }

    @GetMapping("/{id}toys")
    public List<ToyDto> findAllByPet(@PathVariable int id) {
        return toyService.findAllByPet(id);
    }

    @PatchMapping("/{id}/toys/{toyId}")
    public ToyDto findByIdBySpecificPet(@PathVariable int id, @PathVariable int toyId, @RequestBody @Valid UpdateToyCommand command) {
        return toyService.updateBySpecificPet(id, toyId, command);
    }

    @DeleteMapping("/{id}/toys/{toyId}")
    public void softDeleteToy(@PathVariable int id, @PathVariable int toyId) {
        toyService.deltebySpecificPet(id, toyId);
    }

    @PostMapping("/{id}/toys")
    public ToyDto save(@PathVariable int id, @RequestBody @Valid CreateToyCommand command) {
        return toyService.save(id, command);
    }
}
