package io.githuba.mkolodziejczyk92.animalrescue.domain;

import lombok.Value;

@Value

public class Dog implements Animal {

    String id;
    String name;
    Integer age;


    @Override
    public String getName() {
        return "Dog " + name;
    }

    @Override
    public Integer getAge() {
        return age;
    }

    @Override
    public Specie getSpecie() {
        return Specie.DOG;
    }

    @Override
    public String getId() {
        return id;
    }
}
