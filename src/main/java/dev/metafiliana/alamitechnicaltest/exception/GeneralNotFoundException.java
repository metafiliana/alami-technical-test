package dev.metafiliana.alamitechnicaltest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="General not found error")
public class GeneralNotFoundException extends RuntimeException {
}
