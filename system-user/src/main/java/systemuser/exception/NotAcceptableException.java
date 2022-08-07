package systemuser.exception;

import systemuser.enums.ApiStatus;

public class NotAcceptableException extends RuntimeException {
    public NotAcceptableException(ApiStatus status) {
        super(status.name());
    }

}
