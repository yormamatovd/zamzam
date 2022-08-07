package systemuser.exception;

import systemuser.enums.ApiStatus;

public class NotFoundException extends RuntimeException {

    public NotFoundException(ApiStatus status) {
        super(status.name());
    }
}
