package io.githuba.mkolodziejczyk92.animalrescue;

import io.githuba.mkolodziejczyk92.animalrescue.api.dto.AllAnimalsResponse;
import io.githuba.mkolodziejczyk92.animalrescue.api.dto.ErrorDto;
import io.githuba.mkolodziejczyk92.animalrescue.api.dto.SingleAnimalResponse;
import io.githuba.mkolodziejczyk92.animalrescue.domain.Dog;
import io.githuba.mkolodziejczyk92.animalrescue.domain.Specie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AnimalRescueApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void whenApplicationStartShouldCreateRestTemplate() {
        assertThat(restTemplate).isNotNull();
    }

    @Test
    void whenApplicationStartShouldReturnEmptyListOfAnimals() {
        //given
        String getAllAnimalsUrl = "/animals";
        HttpStatus expectedStatusCode = HttpStatus.OK;
        Integer expectedAnimalCount = 0;

        //when
        ResponseEntity<AllAnimalsResponse> animalEntity =
                restTemplate.getForEntity(getAllAnimalsUrl, AllAnimalsResponse.class);

        //then
        assertThat(animalEntity.getStatusCode()).isEqualTo(expectedStatusCode);
        assertThat(animalEntity.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(animalEntity.hasBody()).isTrue();
        assertThat(animalEntity.getBody().getAnimals().isEmpty()).isTrue();
        assertThat(animalEntity.getBody().getTotalAnimals()).isEqualTo(expectedAnimalCount);
    }

    @Test
    void whenUserAddDogApplicationShouldReturnThisAnimalAndListOfAnimalsIsNotEmpty() {
        //given
        String getDogUrl;
        Integer dogAge = 28;
        Specie specie = Specie.DOG;
        String animalBody = """
                {
                "name": "Miauczek",
                "age": 28
                }
                """;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<String> httpEntity = new HttpEntity<>(animalBody, httpHeaders);
        String postAnimalUrl = "/animals/dogs";
        HttpStatus expectedStatusCode = HttpStatus.CREATED;

        //create dog
        ResponseEntity<Dog> addDog
                = restTemplate.postForEntity(postAnimalUrl, httpEntity, Dog.class);
        String dogId = addDog.getBody().getId();
        getDogUrl = "/animals/" + dogId;
        ResponseEntity<SingleAnimalResponse> allDogsEntity
                = restTemplate.getForEntity(getDogUrl, SingleAnimalResponse.class);
        //dog created
        assertThat(addDog.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(allDogsEntity.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(allDogsEntity.getBody().getAge()).isEqualTo(dogAge);
        assertThat(allDogsEntity.getBody().getSpecie()).isEqualTo(specie);

    }

    @ParameterizedTest
    @MethodSource("badRequestArguments")
    void shouldReturnBadRequestWhenAgeIsNegative(String name, Integer age, String message) {
        // given
        String animalBody = """
                {
                "name": "%s",
                "age": %d
                }
                """;
        String formatedAnimalBody = String.format(animalBody, name, age);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<String> httpEntity = new HttpEntity<>(formatedAnimalBody, httpHeaders);
        String postAnimalUrl = "/animals/dogs";
        HttpStatus expectedStatusCode = HttpStatus.BAD_REQUEST;

        //when
        ResponseEntity<ErrorDto> stringResponseEntity
                = restTemplate.postForEntity(postAnimalUrl, httpEntity, ErrorDto.class);

        //then
        assertThat(stringResponseEntity.getStatusCode()).isEqualTo(expectedStatusCode);
        assertThat(stringResponseEntity
                .getBody()
                .getMessage())
                .isEqualTo(message);
    }

    static Stream<Arguments> badRequestArguments() {
        return Stream.of(Arguments.of("Reksio", null, "age: age cannot be null"),
                Arguments.of("", 10, "name: max length 30 and min 2"));
    }

}
