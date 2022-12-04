package io.githuba.mkolodziejczyk92.animalrescue.repositories;

import io.githuba.mkolodziejczyk92.animalrescue.domain.Animal;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalsRepository extends MongoRepository<Animal, String> {

    public Animal findAnimalById(String name);
    public void deleteById(String id);


}
