package order.exception;

import order.enums.ApiStatus;

public class SystemException extends RuntimeException {
    public SystemException(ApiStatus status) {
        super(status.name());
    }
}
