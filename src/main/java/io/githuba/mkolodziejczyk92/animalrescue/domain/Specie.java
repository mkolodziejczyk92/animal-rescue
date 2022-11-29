package io.githuba.mkolodziejczyk92.animalrescue.domain;

import lombok.Getter;

@Getter
public enum Specie {

    DOG("dogs"),
    CAT("cats"),
    ELEPHANT("elephants");

    private final String pluralValue;

    Specie(String pluralValue) {

        this.pluralValue = pluralValue;
    }
}
