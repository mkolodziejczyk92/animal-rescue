package io.githuba.mkolodziejczyk92.animalrescue.repositories;

import io.githuba.mkolodziejczyk92.animalrescue.domain.Animal;

import java.util.List;

public class H2AnimalRepository implements AnimalsRepository{

    @Override
    public void saveAnimal(Animal animal) {

    }
    @Override
    public Animal findAnimal(String id) {
        return null;
    }

    @Override
    public List<Animal> findAllAnimals(Integer limit) {
        return null;
    }

    @Override
    public void deleteAnimal(String id) {

    }
}
