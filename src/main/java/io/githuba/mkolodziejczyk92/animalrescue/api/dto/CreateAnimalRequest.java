package io.githuba.mkolodziejczyk92.animalrescue.api.dto;

import lombok.Value;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Value
public class CreateAnimalRequest {

    @NotNull(message = "name cannot be null")
    @NotBlank(message = "name cannot be empty")
    @Length(message = "max length 30 and min 2", min = 2, max = 30)
    String name;

    @NotNull(message = "age cannot be null")
    @Positive(message = "age must be positive")
    @Max(message = "max age 200", value = 200)
    Integer age;
}
