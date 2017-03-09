package edu.vse.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerErrorException extends RuntimeException {

    public InternalServerErrorException() {
    }

    public InternalServerErrorException(String s) {
        super(s);
    }

    public InternalServerErrorException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public InternalServerErrorException(Throwable throwable) {
        super(throwable);
    }

    public InternalServerErrorException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
