package register.exceptions;

import register.enums.ApiStatus;

public class NotAcceptableException extends RuntimeException {
    public NotAcceptableException(ApiStatus status) {
        super(status.name());
    }

}
