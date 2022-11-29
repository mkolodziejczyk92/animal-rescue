package io.githuba.mkolodziejczyk92.animalrescue.api.dto;

import lombok.Value;
import org.springframework.http.HttpStatus;

import java.time.Instant;
@Value
public class ErrorDto {

    private String message;
    private Instant timestamp;
    private HttpStatus httpStatusCode;

}
