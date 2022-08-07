package systemuser.exception;

import systemuser.enums.ApiStatus;

public class SystemException extends RuntimeException {
    public SystemException(ApiStatus status) {
        super(status.name());
    }
}
