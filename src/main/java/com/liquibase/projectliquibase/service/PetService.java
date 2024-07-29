package com.liquibase.projectliquibase.service;

import com.liquibase.projectliquibase.exception.PetNotFoundExpetion;
import com.liquibase.projectliquibase.model.Pet;
import com.liquibase.projectliquibase.model.command.create.CreatePetCommand;
import com.liquibase.projectliquibase.model.command.update.UpdatePetCommand;
import com.liquibase.projectliquibase.model.dto.PetDto;
import com.liquibase.projectliquibase.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetService {


    private final PetRepository petRepository;

    public PetDto save(CreatePetCommand command) {
        Pet toSave = command.toEntity();
        return PetDto.fromEntity(petRepository.save(toSave));
    }

    public List<PetDto> findAll() {
        return petRepository.findAll().stream()
                .map(PetDto::fromEntity)
                .collect(Collectors.toList());
    }


    public PetDto findById(int id) {
        return petRepository.findById(id)
                .map(PetDto::fromEntity)
                .orElseThrow(() -> new PetNotFoundExpetion(MessageFormat
                        .format("Pet with id {0} not found", id)));
    }


    public PetDto update(int id, UpdatePetCommand command) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new PetNotFoundExpetion(MessageFormat
                        .format("Pet with id {0} not found", id)));

        if (command.getName() != null) {
            pet.setName(command.getName());
        }

        if (command.getType() != null) {
            pet.setType(command.getType());
        }

        if (command.getBirthDate() != null) {
            pet.setBirthDate(command.getBirthDate());
        }


        return PetDto.fromEntity(petRepository.save(pet));
    }

    public void deleteById(int id) {
        petRepository.findById(id)
                .map(PetDto::fromEntity)
                .orElseThrow(() -> new PetNotFoundExpetion((MessageFormat
                        .format("Pet with id {0} not found", id))));
        petRepository.deleteById(id);
    }
}