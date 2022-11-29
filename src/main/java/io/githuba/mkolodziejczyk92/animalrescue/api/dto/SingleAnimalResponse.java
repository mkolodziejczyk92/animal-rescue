package io.githuba.mkolodziejczyk92.animalrescue.api.dto;

import io.githuba.mkolodziejczyk92.animalrescue.domain.Specie;
import lombok.Value;

@Value
public class SingleAnimalResponse {

    Integer age;
    Specie specie;
}
