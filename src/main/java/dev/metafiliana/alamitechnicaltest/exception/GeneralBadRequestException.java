package dev.metafiliana.alamitechnicaltest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="General bad request error")
public class GeneralBadRequestException extends RuntimeException {
}
