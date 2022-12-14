package io.githuba.mkolodziejczyk92.animalrescue.infrastructure;

import io.githuba.mkolodziejczyk92.animalrescue.domain.*;
import io.githuba.mkolodziejczyk92.animalrescue.repositories.AnimalsDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@Profile(value = "profile_two")
public class DefaultAnimalServiceCopy implements AnimalService {

    private final AnimalsDao animalsRepository;


    public DefaultAnimalServiceCopy(AnimalsDao animalsRepository) {
        this.animalsRepository = animalsRepository;
    }

    @Override
    public Page<Animal> listOfAnimal(Pageable pageable) {
        return animalsRepository.findAnimals(pageable);

    }

    @Override
    public Animal singleAnimal(String id) {
        return animalsRepository.findAnimal(id);
    }


    @Override
    public Animal createAnimal(Specie specie, Integer age, String name, String id) {
        String animalId;
        if (id == null) {
            animalId = UUID.randomUUID().toString();
        } else {
            animalId = id;
        }
        Animal animal;
        switch (specie) {
            case DOG -> {
                animal = new Dog(animalId, name, age);
            }
            case CAT -> {
                animal = new Cat(animalId, name, age);
            }
            case ELEPHANT -> {
                animal = new Elephant(animalId, name, age);
            }
            default -> {

                throw new IllegalArgumentException("Unsupported specie");
            }
        }
        animalsRepository.saveAnimal(animal);
        return animal;
    }

    @Override
    public boolean deleteAnimal(String id) {
        animalsRepository.deleteAnimal(id);
        log.info("Deleting animal by ID {}", id);
        return true;
    }

    @Override
    public boolean animalExist(String id) {
        return animalsRepository.findAnimal(id) != null;
    }
}
