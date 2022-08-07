package register.exceptions;

import register.enums.ApiStatus;

public class BadRequestException extends RuntimeException {

    public BadRequestException(ApiStatus status) {
        super(status.name());
    }
}
