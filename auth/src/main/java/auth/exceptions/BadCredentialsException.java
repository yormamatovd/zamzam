package auth.exceptions;

import auth.enums.ApiStatus;

public class BadCredentialsException extends RuntimeException {
    public BadCredentialsException(ApiStatus status) {
        super(status.name());
    }
}
