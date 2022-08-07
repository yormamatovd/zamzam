package order.exception;

import order.enums.ApiStatus;

public class NotAcceptableException extends RuntimeException {
    public NotAcceptableException(ApiStatus status) {
        super(status.name());
    }

}
