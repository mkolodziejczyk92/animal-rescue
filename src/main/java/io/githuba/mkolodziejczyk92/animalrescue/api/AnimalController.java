package io.githuba.mkolodziejczyk92.animalrescue.api;

import io.githuba.mkolodziejczyk92.animalrescue.api.dto.CreateAnimalRequest;
import io.githuba.mkolodziejczyk92.animalrescue.api.dto.ErrorDto;
import io.githuba.mkolodziejczyk92.animalrescue.domain.Animal;
import io.githuba.mkolodziejczyk92.animalrescue.domain.Specie;
import io.githuba.mkolodziejczyk92.animalrescue.infrastructure.AnimalService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping
@Slf4j
@AllArgsConstructor
public class AnimalController {

    private final AnimalService animalService;


    @GetMapping(path = "/animals")
    public ResponseEntity<Page<Animal>> getAnimals(@RequestParam(required = false, defaultValue = "10") Integer limit,
                                                   @RequestParam Integer size,
                                                   @RequestParam Integer page,
                                                   @RequestParam(required = false, defaultValue = "age") String sort,
                                                   @RequestParam(required = false, defaultValue = "DESC") String direction) {

        Sort sortBy = Sort.by(Sort.Direction.fromString(direction), sort);
        PageRequest of = PageRequest.of(page, size, sortBy);
        return ResponseEntity.ok().body(animalService.listOfAnimal(of));
    }

    @GetMapping("/animals/{id}") //rzeczy ktore wysylamy na front
    public ResponseEntity<Animal> getById(@PathVariable String id) {
        log.info(id);
        Animal singleAnimal = animalService.singleAnimal(id);
        if (singleAnimal == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(singleAnimal);
        }
    }

    @PutMapping(path = "/animals/{specie}/{id}")
    public ResponseEntity<Animal> upsertAnimal(
            @PathVariable String id,
            @PathVariable String specie,
            @RequestBody CreateAnimalRequest request) {
        boolean ifExist = animalService.animalExist(id);
        Animal animal =
                animalService.createAnimal(parseStringToSpecie(specie),
                        request.getAge(),
                        request.getName(),
                        id);
        log.info("Animal exist: " + ifExist);

        return ResponseEntity.ok().body(animal);

    }


    @PostMapping(path = "/animals/{specie}") //rzeczy ktore wysylamy (pobieramy) z frontu i tworzymy nowe zasoby
    public ResponseEntity<Animal> addAnimal(
            @Valid
            @RequestBody CreateAnimalRequest request,
            @PathVariable String specie) {
        log.info(request.toString());
        log.info(specie);
        Animal animal =
                animalService.createAnimal(parseStringToSpecie(specie),
                        request.getAge(),
                        request.getName(),
                        null);

        return ResponseEntity.created(URI.create("/animals")).body(animal);
    }

    private Specie parseStringToSpecie(String rawSpecie) {
        return Arrays.stream(Specie.values()).
                filter(s -> s.getPluralValue().equals(rawSpecie)).
                findFirst().get();

    }

    @DeleteMapping(path = "/animals/{id}")
    public ResponseEntity<Animal> deleteAnimal(@PathVariable String id) {
        animalService.deleteAnimal(id);
        return ResponseEntity.noContent().build();
    }

    // exception handler - jezeli poleci taki wyjatek to wywolaj mi taka metode i zwroc wartosc ktora podam
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleValidationException(MethodArgumentNotValidException exception) {
        Map<String, String> errorMap = new HashMap<>();
        exception.getBindingResult().getAllErrors().
                forEach(s -> errorMap.put(((FieldError) s).getField(), s.getDefaultMessage()));
        log.info(exception.getBindingResult().toString());
        String message = errorMap.entrySet()
                .stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining(", "));
        return ResponseEntity.badRequest().body(new ErrorDto(message, Instant.now(), HttpStatus.BAD_REQUEST));

    }

}
