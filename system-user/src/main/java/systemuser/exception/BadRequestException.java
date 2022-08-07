package systemuser.exception;

import systemuser.enums.ApiStatus;

public class BadRequestException extends RuntimeException {

    public BadRequestException(ApiStatus status) {
        super(status.name());
    }
}
