package edu.vse.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ImageConflictException extends RuntimeException {

    public ImageConflictException() {
    }

    public ImageConflictException(String s) {
        super(s);
    }

    public ImageConflictException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ImageConflictException(Throwable throwable) {
        super(throwable);
    }

    public ImageConflictException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
