package info.exception;

import info.enums.ApiStatus;

public class NotAcceptableException extends RuntimeException {
    public NotAcceptableException(ApiStatus status) {
        super(status.name());
    }

}
