package info.exception;

import info.enums.ApiStatus;

public class NotFoundException extends RuntimeException{

    public NotFoundException(ApiStatus status) {
        super(status.name());
    }
}
