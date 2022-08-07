package auth.exceptions;

import auth.enums.ApiStatus;

public class NotFoundException extends RuntimeException{
    public NotFoundException(ApiStatus status) {
        super(status.name());
    }
}
