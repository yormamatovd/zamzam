package order.exception;

import order.enums.ApiStatus;

public class NotFoundException extends RuntimeException {

    public NotFoundException(ApiStatus status) {
        super(status.name());
    }
}
