package com.liquibase.projectliquibase.service;


import com.liquibase.projectliquibase.exception.NegativePriceException;
import com.liquibase.projectliquibase.exception.PetNotFoundExpetion;
import com.liquibase.projectliquibase.exception.TooManyToysException;
import com.liquibase.projectliquibase.exception.ToyNotFoundExpetion;
import com.liquibase.projectliquibase.model.Pet;
import com.liquibase.projectliquibase.model.Toy;
import com.liquibase.projectliquibase.model.command.create.CreateToyCommand;
import com.liquibase.projectliquibase.model.command.update.UpdateToyCommand;
import com.liquibase.projectliquibase.model.dto.PetDto;
import com.liquibase.projectliquibase.model.dto.ToyDto;
import com.liquibase.projectliquibase.repository.PetRepository;
import com.liquibase.projectliquibase.repository.ToyRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ToyService {

    private final PetRepository petRepository;
    private final ToyRepository toyRepository;


    public ToyDto save(int id, CreateToyCommand command) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new PetNotFoundExpetion(MessageFormat
                        .format("Pet with id {0} not found", id)));
        if (pet.getToys().size() > 2) {
            throw new TooManyToysException(MessageFormat
                    .format("Pet already have 3 toys", id));
        }

        Toy toSave = command.toEntity();
        toSave.setPet(pet);
        return ToyDto.formEntity(toyRepository.save(toSave));
    }


    public List<ToyDto> findAllByPet(int petId) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new PetNotFoundExpetion(MessageFormat
                        .format("Pet with id {0} not found", petId)));

        return pet.getToys().stream()
                .map(ToyDto::formEntity)
                .collect(Collectors.toList());
    }

    public ToyDto findByIdSpecificPet(int petId, int toyId) {
        Toy toy = toyRepository.findById(toyId)
                .orElseThrow(() -> new ToyNotFoundExpetion(MessageFormat
                        .format("Toy with id {0} not found", toyId)));

        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new PetNotFoundExpetion(MessageFormat
                        .format("Pet with id {0} not found", petId)));

        return pet.getToys().stream()
                .filter(p -> p.getId() == toy.getId())
                .findFirst().map(ToyDto::formEntity)
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat
                        .format("Pet with id {0}, doesn't have gift with id {1}", pet, toyId)));
    }


    public ToyDto updateBySpecificPet(int petId, int toyId, UpdateToyCommand command) {
        if (command.getPrice() < 0) {
            throw new NegativePriceException("Negative price");
        }

        Toy toy = toyRepository.findById(toyId)
                .orElseThrow(() -> new ToyNotFoundExpetion(MessageFormat
                        .format("Toy with id {0} not found", toyId)));


        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new PetNotFoundExpetion(MessageFormat
                        .format("Pet with id  {0} not found", petId)));

        pet.getToys().stream()
                .filter(p -> p.getId() == toy.getId())
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat
                        .format("Pet with id {0, doesn't have toy with id {1}", pet, toyId)));
        if (command.getPrice() != 0.0) {
            toy.setPrice(command.getPrice());
        }

        if (command.getName() != null) {
            toy.setName(command.getName());
        }
        return ToyDto.formEntity(toyRepository.save(toy));
    }


    public void deltebySpecificPet(int petId, int toyId) {
        Toy toy = toyRepository.findById(toyId)
                .orElseThrow(() -> new ToyNotFoundExpetion(MessageFormat
                        .format("Toy with id {0} not found", toyId)));

        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new PetNotFoundExpetion(MessageFormat
                        .format("Pet with id {0} not found", petId)));

        pet.getToys().stream()
                .filter(p -> p.getId() == toy.getId())
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat
                        .format("Pet with id {0}, doesn't have toy with id {1}", pet, toyId)));

        toyRepository.deleteById(toyId);
    }
}
