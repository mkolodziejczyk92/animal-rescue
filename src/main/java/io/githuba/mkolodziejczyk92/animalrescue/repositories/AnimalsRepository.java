package io.githuba.mkolodziejczyk92.animalrescue.repositories;

import io.githuba.mkolodziejczyk92.animalrescue.domain.Animal;

import java.util.List;

public interface AnimalsRepository {

    public void saveAnimal(Animal animal);
    Animal findAnimal(String id);
    List<Animal> findAllAnimals(Integer limit);

    void deleteAnimal(String id);
}
