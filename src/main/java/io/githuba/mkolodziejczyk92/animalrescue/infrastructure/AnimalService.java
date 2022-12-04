package io.githuba.mkolodziejczyk92.animalrescue.infrastructure;

import io.githuba.mkolodziejczyk92.animalrescue.domain.Animal;
import io.githuba.mkolodziejczyk92.animalrescue.domain.Specie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AnimalService {

    public Page<Animal> listOfAnimal(Pageable pageable);
    public Animal singleAnimal(String id);
    public Animal createAnimal(Specie specie, Integer age, String name, String id);
    boolean deleteAnimal(String id);
    boolean animalExist(String id);
}
