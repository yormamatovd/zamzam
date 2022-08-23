package auth.exceptions;

import auth.enums.ApiStatus;

public class SystemException extends RuntimeException {
    public SystemException(ApiStatus status) {
        super(status.name());
    }
}
