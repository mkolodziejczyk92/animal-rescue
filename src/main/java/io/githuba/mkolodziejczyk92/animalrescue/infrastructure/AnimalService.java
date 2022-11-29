package io.githuba.mkolodziejczyk92.animalrescue.infrastructure;

import io.githuba.mkolodziejczyk92.animalrescue.domain.Animal;
import io.githuba.mkolodziejczyk92.animalrescue.domain.Specie;

import java.util.List;

public interface AnimalService {

    public List<Animal> listOfAnimal(Integer limit);
    public Animal singleAnimal(String id);
    public Animal createAnimal(Specie specie, Integer age, String name, String id);
    boolean deleteAnimal(String id);
    boolean animalExist(String id);
}
