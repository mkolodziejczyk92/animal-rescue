package io.githuba.mkolodziejczyk92.animalrescue.domain;

import lombok.Value;

@Value
public class Cat implements Animal {

    String id;
    String name;
    Integer age;



    @Override
    public Specie getSpecie() {

        return Specie.CAT;
    }
}
