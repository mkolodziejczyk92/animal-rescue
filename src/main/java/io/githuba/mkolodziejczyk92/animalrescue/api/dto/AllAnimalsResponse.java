package io.githuba.mkolodziejczyk92.animalrescue.api.dto;

import io.githuba.mkolodziejczyk92.animalrescue.domain.Animal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class AllAnimalsResponse {

    List<Animal> animals;
    Integer totalAnimals;


    public AllAnimalsResponse(List<Animal> animals) {
        this.totalAnimals = animals.size();
        this.animals = animals;
    }
}
