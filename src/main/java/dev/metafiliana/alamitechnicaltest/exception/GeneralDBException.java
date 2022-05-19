package dev.metafiliana.alamitechnicaltest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR, reason="General DB error")
public class GeneralDBException extends RuntimeException {
}
